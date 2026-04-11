import { message } from 'antd';
import Upload, { RcFile } from 'antd/es/upload';

import { FileSuffix } from '@/config/file-bucket.enum';
import { Maximum } from '@/config/constants';

/**
 * 用于文件上传时限制的文件大小和类型
 * @param file
 * @returns
 */
export const beforeUpload = (file: RcFile) => {
  const isImage = FileSuffix.isImage(file.type);

  if (!isImage) {
    message.error('You can only upload Image file!');
    // Upload.LIST_IGNORE 表示上传错误忽略显示
    return Upload.LIST_IGNORE;
  }
  const isLt10M = file.size / 1024 / 1024 < Maximum;
  if (!isLt10M) {
    message.error('Image must smaller than 10MB!');
    return Upload.LIST_IGNORE;
  }
  return isImage && isLt10M;
};
