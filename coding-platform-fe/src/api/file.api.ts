import { FileDto, FileRsp } from '@/interfaces/common/file.interface';

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
export function deleteFile(dto: FileDto) {
  return http.post<void>('/file/delete.json', dto);
}

/**
 * 文件下载
 */
export function downloadFile(dto: FileDto) {
  return http.post<void>('/file/download.do', dto, { responseType: 'blob' });
}
