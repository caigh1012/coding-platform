import React from 'react';
import { Tabs } from 'antd';

import type { TabsProps } from 'antd';

import UploadOne from './components/upload-one/upload-one';
import UploadTwo from './components/upload-two/upload-two';
import './file-upload.scss';

const items: TabsProps['items'] = [
  {
    key: '1',
    label: '方式1',
    children: <UploadOne />,
  },
  {
    key: '2',
    label: '方式2',
    children: <UploadTwo />,
  },
  {
    key: '3',
    label: '方式3',
    children: '大文件上传',
  },
];

const FileUpload: React.FC = () => {
  return (
    <div styleName="wrapper">
      <Tabs
        defaultActiveKey="1"
        items={items}
      />
    </div>
  );
};

export default FileUpload;
