import { DeleteFileDto, FileRsp } from '@/interfaces/common/file.interface';

import { http } from './http';

/**
 * 文件上传
 */
export function uploadFile(formData: FormData) {
  return http.post<FileRsp>('/file/upload.json', formData, { headers: { 'Content-Type': 'multipart/form-data' } });
}

/**
 * 文件删除
 */
export function deleteFile(dto: DeleteFileDto) {
  return http.post<void>('/file/delete.json', dto);
}
