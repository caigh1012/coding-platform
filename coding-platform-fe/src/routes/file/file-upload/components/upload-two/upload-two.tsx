import { useEventListener } from 'ahooks';
import React, { useRef, useState } from 'react';
import { Button, message } from 'antd';

import { uploadFile } from '@/api/file.api';
import { FileBucketEnum } from '@/config/file-bucket.enum';
const UploadTwo: React.FC = () => {
  const [files, setFiles] = useState<Record<number, File>>({});
  const inputRef = useRef(null);

  useEventListener(
    'change',
    (e) => {
      const files = e.target.files || [];
      setFiles(files);
    },
    { target: inputRef },
  );

  function submitUpload() {
    const params = Object.values(files).map((item) => {
      const formData = new FormData();
      formData.append('file', item);
      formData.append('bucket', FileBucketEnum.UserInfo);
      return uploadFile(formData);
    });

    Promise.allSettled(params).then((res) => {
      if (res) {
        const isCompelete = res.every((item) => item.status === 'fulfilled');
        if (isCompelete) {
          message.success('文件上传成功');
        }
      }
    });
  }

  return (
    <>
      <p>原生input元素结合 FormData 以及 axios 进行上传</p>
      <br />
      <input
        multiple
        ref={inputRef}
        type="file"
        name="file"
      />
      <br />
      <br />

      <Button
        type="primary"
        onClick={() => submitUpload()}>
        提交上传
      </Button>
    </>
  );
};

export default UploadTwo;
