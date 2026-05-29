import { expose } from 'comlink';
import SparkMD5 from 'spark-md5';

import { ChunkSize } from '@/config/constants';

export interface Chunk {
  chunk: Blob;
  fileHash: string;
  index: number;
}

export interface FileUploadWorkerApi {
  getFileHash: (file: File) => Promise<string>;
  computeHash: (file: File) => Promise<string>;
  createChunks: (file: File, fileHash: string) => Chunk[];
}

class FileUploadWorker implements FileUploadWorkerApi {
  /**
   * 这个是对整个文件进行 hash 计算，一次性将整个文件加载到内存。对于大文件（如几十GB），浏览器很容易内存溢出，导致页面奔溃或浏览器直接挂掉
   */
  getFileHash(file: File): Promise<string> {
    return new Promise((resolve) => {
      const fileReader = new FileReader();
      fileReader.readAsArrayBuffer(file);
      fileReader.onload = function (e) {
        let fileMd5 = SparkMD5.ArrayBuffer.hash(e.target?.result as ArrayBuffer);
        resolve(fileMd5);
      };
    });
  }

  /**
   * 这个属于增量算法对文件进行hash算法
   */
  computeHash(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const blobSlice = File.prototype.slice;
      const spark = new SparkMD5.ArrayBuffer();
      const fileReader = new FileReader();
      let chunkSize = ChunkSize;
      // Math.ceil 取最大值
      let chunks = Math.ceil(file.size / chunkSize);
      let currentChunk = 0;

      fileReader.onload = function (e) {
        spark.append(e.target?.result as ArrayBuffer);
        currentChunk++;

        if (currentChunk < chunks) {
          loadNext();
        } else {
          resolve(spark.end());
        }
      };

      fileReader.onerror = function () {
        reject('oops, something went wrong.');
      };

      function loadNext() {
        var start = currentChunk * chunkSize,
          end = start + chunkSize >= file.size ? file.size : start + chunkSize;
        fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
      }

      loadNext();
    });
  }

  createChunks(file: File, fileHash: string) {
    // 接受一个文件对象，要把这个文件对象切片，返回一个切片数组
    const chunks = [];
    // 文件大小.slice(开始位置,结束位置)
    let start = 0;
    let index = 0;
    while (start < file.size) {
      let end = Math.min(start + ChunkSize, file.size);
      let chunk = file.slice(start, end);
      chunks.push({
        chunk,
        fileHash,
        index,
      });

      index++;
      start += ChunkSize;
    }

    return chunks;
  }
}

expose(new FileUploadWorker());
