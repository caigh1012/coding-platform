import { Button, Upload } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import { UploadChangeParam } from 'antd/es/upload';
import { useState } from 'react';

import { useTokenStore } from '@/models/store';
import { FileRsp } from '@/interfaces/common/file.interface';
import { beforeUpload } from '@/utils/before-upload';
import { FileBucket } from '@/config/file-bucket.enum';
import { deleteFile, downloadFile } from '@/api/file.api';

import type { UploadFile } from 'antd';

const UploadOne: React.FC = () => {
  const { token } = useTokenStore();
  const [files, setFiles] = useState<UploadFile<FileRsp>[]>([]);

  const onFileChange = (Event: UploadChangeParam) => {
    const { fileList } = Event;

    const list = fileList.map((item) => {
      const data = item?.response?.data;
      return {
        uid: data?.fileId || item.uid,
        name: item.name,
        status: item.status,
        url: item.url || data?.fileUrl,
      };
    });

    setFiles(list);
  };

  function removeFile(file: UploadFile) {
    return deleteFile({ fileId: file.uid, bucket: FileBucket.UserInfo });
  }

  function download(file: UploadFile) {
    downloadFile({ fileId: file.uid, bucket: FileBucket.UserInfo });
  }

  return (
    <>
      <p>基于 antd 的 Upload 组件进行文件上传，原则上是原生input的封装</p>
      <p>实际请求体为 content-type: multipart/form-data; </p>
      <br />
      <Upload
        beforeUpload={beforeUpload}
        action={apiPrefix + '/file/upload.json'}
        name="file"
        multiple
        maxCount={10}
        headers={{ Authorization: token || '' }}
        data={{ bucket: FileBucket.UserInfo }}
        fileList={files}
        listType="picture"
        showUploadList={{ showDownloadIcon: true, showPreviewIcon: true, showRemoveIcon: true }}
        onChange={onFileChange}
        onDownload={(file) => download(file)}
        onRemove={(file) => removeFile(file)}>
        <Button icon={<UploadOutlined />}>Upload</Button>
      </Upload>
    </>
  );
};

export default UploadOne;
