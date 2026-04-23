package cn.caigh.coding_platform.pojo.dto.common;

public class FileDto {
  private String fileId;
  private String bucket;

  public FileDto() {
  }

  @Override
  public String toString() {
    return "DeleteFileDto{" +
        "fileId='" + fileId + '\'' +
        ", bucket='" + bucket + '\'' +
        '}';
  }

  public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  public String getBucket() {
    return bucket;
  }

  public void setBucket(String bucket) {
    this.bucket = bucket;
  }
}
