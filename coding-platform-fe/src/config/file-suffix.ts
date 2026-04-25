/**
 * 文件上传后缀限制
 */
class FileSuffix {
  private static readonly imagesSuffix: string[] = ['image/jpg', 'image/jpeg', 'image/png', 'image/gif', 'image/webp'];

  static isImage(suffix: string) {
    return FileSuffix.imagesSuffix.findIndex((item) => item === suffix) > -1;
  }
}

export { FileSuffix };
