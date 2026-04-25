import React from 'react';
import { Button, Space } from 'antd';

import './file-download.scss';

const FileDownload: React.FC = () => {
  return (
    <div styleName="wrapper">
      <Button type="primary">文件下载</Button>
      <br></br>
      <br></br>
      <Space>
        <Button type="primary">按钮A</Button>
        <Button type="primary">按钮B</Button>
        <Button type="primary">按钮C</Button>
      </Space>
    </div>
  );
};

export default FileDownload;
