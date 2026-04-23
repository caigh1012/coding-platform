package cn.caigh.coding_platform.controller;

import cn.caigh.coding_platform.pojo.dto.common.FileDto;
import cn.caigh.coding_platform.pojo.vo.common.File;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileCtrl {
  @Autowired
  private FileService fileService;


  @PostMapping(value = "/file/upload.json")
  @ResponseBody
  public ResultVo<File> uploadFile(@RequestParam("file") MultipartFile file, String bucket) throws Exception {
    return fileService.uploadFile(file, bucket);
  }

  @PostMapping(value = "/file/delete.json")
  @ResponseBody
  public ResultVo<String> uploadFile(@RequestBody FileDto deleteFileDto) {
    return fileService.deleteFile(deleteFileDto.getFileId(), deleteFileDto.getBucket());
  }

  @PostMapping(value = "/file/download.do")
  public void downloadFile(@RequestBody FileDto downloadFileDto, HttpServletResponse response) {
    fileService.downloadFile(downloadFileDto.getFileId(), downloadFileDto.getBucket(), response);
  }
}
