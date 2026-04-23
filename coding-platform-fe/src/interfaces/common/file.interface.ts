/**
 * 文件上传后的文件
 */
export interface FileRsp {
  /**
   * 文件id
   */
  readonly fileId: string;
  /**
   * 文件名称
   */
  readonly fileName: string;
  /**
   * 文件url
   */
  readonly fileUrl: string;
}

/**
 * 文件请求（删除、下载）
 */
export interface FileDto {
  /**
   * 文件id
   */
  readonly fileId: string;
  /**
   * bucket
   */
  readonly bucket: string;
}
