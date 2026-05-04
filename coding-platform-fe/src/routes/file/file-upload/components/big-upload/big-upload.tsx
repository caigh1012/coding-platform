import React, { ChangeEvent, useEffect, useRef, useState } from 'react';
import { message, Progress } from 'antd';
import axios from 'axios';
import * as Comlink from 'comlink';
import { useSetState } from 'ahooks';

import { Chunk } from '@/helpers/worker/flie-upload.worker';
import { paralleTask } from '@/utils/paralle-task';
import { ChunkSize } from '@/config/constants';
import { createFileWorker } from '@/helpers/worker';

interface IFileInfo {
  fileHash: string;
  fileName: string;
}

const BigUpload: React.FC = () => {
  const [fileInfo, setFileInfo] = useSetState<IFileInfo>({ fileHash: '', fileName: '' });
  const [percent, setPercent] = useState(0);
  const [chunksProgress, setChunksProgress] = useSetState<Record<string, boolean>>({});
  const [totalChunks, setTotalChunks] = useState(0);
  const workerRef = useRef<SafeAny>(null);

  const onFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    const files = e?.target?.files;
    if (files) {
      const file = files[0];
      startUpload(file);
    }
  };

  async function startUpload(file: File) {
    /**
     * 上传前的初始化
     */
    let totalChunks = Math.ceil(file.size / ChunkSize);
    setTotalChunks(totalChunks);
    setChunksProgress({});

    // 计算文件hash
    let fileHash = await workerRef.current.fileWorkerApi.computeHash(file);

    const { data } = await axios.post('http://127.0.0.1:19528/upload/init', {
      fileHash,
      fileName: file.name,
    });

    // chunksProgress 中间变量
    let chunksProgressMiddle: Record<number, true> = {};

    // 实现秒传的功能
    if (data.exist) {
      for (let i = 0; i < totalChunks; i++) {
        chunksProgressMiddle[`${i}`] = true;
      }
      setChunksProgress(chunksProgressMiddle);
      return;
    }

    setFileInfo({ fileHash, fileName: file.name });

    // 2.标记已上传的分片
    const uploaded = data.uploaded || [];
    uploaded.forEach((idx: number) => {
      chunksProgressMiddle[idx] = true;
    });
    setChunksProgress(chunksProgressMiddle);

    // 3. 找出需要上传的分片
    let chunks = await workerRef.current.fileWorkerApi.createChunks(file, fileHash);
    const needUpload = [];
    for (let i = 0; i < totalChunks; i++) {
      // 不咋 chunksProessMiddle 中的需要加入到 needUpload 进行上传
      if (!chunksProgressMiddle[i]) needUpload.push(chunks[i]);
    }

    if (needUpload.length === 0) {
      return;
    }

    // 4. 开始上传缺失分片（并发）
    uploadChunks(needUpload);
  }

  async function uploadChunks(chunks: Chunk[]) {
    const tasks = chunks.map((item: Chunk) => {
      return () => uploadChunkWithRetry(item);
    });

    return paralleTask(tasks, 3);
  }

  function uploadChunkWithRetry(item: Chunk): Promise<void> {
    return new Promise((resolve, reject) => {
      const { index } = item;
      let formData = new FormData();
      formData.append('fileHash', item.fileHash);
      formData.append('index', `${item.index}`);
      formData.append('chunk', item.chunk);
      axios
        .post('http://127.0.0.1:19528/upload/chunk', formData)
        .then(() => {
          setChunksProgress({ [`${index}`]: true });
          resolve();
        })
        .catch(() => {
          reject(new Error(`分片 ${index} 上传失败`));
        });
    });
  }

  async function mergeFile(fileHash: string, fileName: string, totalChunks: number) {
    try {
      await axios.post('http://127.0.0.1:19528/upload/merge', {
        fileHash,
        fileName,
        totalChunks,
      });
    } catch (err) {
      message.error('合并失败:' + err);
    }
  }

  useEffect(() => {
    const { fileWorker, fileWorkerApi } = createFileWorker();
    workerRef.current = { fileWorker, fileWorkerApi };
    return () => {
      fileWorkerApi[Comlink.releaseProxy]?.();
      fileWorker.terminate();
    };
  }, []);

  useEffect(() => {
    if (fileInfo.fileHash) {
      if (Object.keys(chunksProgress).length === totalChunks && Object.values(chunksProgress).every(Boolean)) {
        mergeFile(fileInfo.fileHash, fileInfo.fileName, totalChunks);
      }
    }
  }, [totalChunks, chunksProgress, fileInfo]);

  useEffect(() => {
    // 是否更新进度条
    const done = Object.values(chunksProgress).filter(Boolean).length;
    const percent = totalChunks > 0 ? Math.round((done / totalChunks) * 100) : 0;

    // eslint-disable-next-line react-hooks/set-state-in-effect
    setPercent(percent);
  }, [totalChunks, chunksProgress]);

  return (
    <div>
      <br />
      <br />
      <input
        type="file"
        onChange={onFileChange}
      />
      <Progress percent={percent} />
    </div>
  );
};

export default BigUpload;
