package cn.caigh.coding_platform.service;

import cn.caigh.coding_platform.pojo.vo.common.File;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
  public ResultVo<File> uploadFile(MultipartFile file, String bucket);

  public ResultVo<String> deleteFile(String fileId, String bucket);

  public void downloadFile(String fileId, String bucket, HttpServletResponse response);
}
