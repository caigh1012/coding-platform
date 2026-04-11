package cn.caigh.coding_platform.controller;

import cn.caigh.coding_platform.pojo.dto.common.DeleteFileDto;
import cn.caigh.coding_platform.pojo.vo.common.File;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileCtrl {
  @Autowired
  private FileService fileService;


  @PostMapping(value = "/file/upload.json")
  public ResultVo<File> uploadFile(@RequestParam("file") MultipartFile file, String bucket) throws Exception {
    return fileService.uploadFile(file, bucket);
  }

  @PostMapping(value = "/file/delete.json")
  public ResultVo<String> uploadFile(@RequestBody DeleteFileDto deleteFileDto) {
    return fileService.deleteFile(deleteFileDto.getFileId(), deleteFileDto.getBucket());
  }
}
