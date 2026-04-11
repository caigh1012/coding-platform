package cn.caigh.coding_platform.pojo.vo.common;

public class File {
  /**
   * 文件id
   */
  private String fileId;
  /**
   * 文件名称
   */
  private String fileName;
  /**
   * 文件访问url
   */
  private String fileUrl;

  public File() {
  }

  @Override
  public String toString() {
    return "FileVo{" +
        "fileId='" + fileId + '\'' +
        ", fileName='" + fileName + '\'' +
        ", fileUrl='" + fileUrl + '\'' +
        '}';
  }

  public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }
}
