package cn.caigh.coding_platform.service.impl;

import cn.caigh.coding_platform.pojo.vo.common.File;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.service.FileService;
import cn.hutool.core.util.IdUtil;
import io.minio.*;
import io.minio.http.Method;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class FileServiceImpl implements FileService {
  @Value("${minio.endpoint}")
  private String endpoint;

  private final MinioClient minioClient;

  public FileServiceImpl(MinioClient minioClient) {
    this.minioClient = minioClient;
  }

  /**
   * 文件上传
   */
  public ResultVo<File> uploadFile(MultipartFile file, String bucket) {
    // 生成唯一文件名（UUID + 原始后缀）
    String originalFilename = file.getOriginalFilename();
    String suffix = "";
    if (originalFilename != null && originalFilename.contains(".")) {
      suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
    }
    String fileId = IdUtil.simpleUUID() + suffix;

    try {
      // 检查桶是否存在，不存在则创建
      boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
      if (!bucketExists) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
      }
    } catch (Exception e) {
      return ResultVo.failed("文件上传失败");
    }

    try {
      // 上传文件
      minioClient.putObject(
          PutObjectArgs.builder()
              .bucket(bucket)
              .object(fileId)
              .stream(file.getInputStream(), file.getSize(), -1)
              .contentType(file.getContentType())
              .build()
      );

      minioClient.getPresignedObjectUrl(
          GetPresignedObjectUrlArgs.builder()
              .method(Method.GET)
              .bucket(bucket)
              .object(fileId)
              .build()
      );

      // 4. 直接拼接公开访问 URL（不带任何签名参数）
      String publicUrl = String.format("%s/%s/%s", endpoint, bucket, fileId);

      File fv = new File();
      fv.setFileId(fileId);
      fv.setFileName(null);
      fv.setFileUrl(publicUrl);
      return ResultVo.success(fv);

    } catch (Exception e) {
      return ResultVo.failed("文件上传失败");
    }
  }

  /**
   * 文件删除
   */
  public ResultVo<String> deleteFile(String fileId, String bucket) {
    try {
      minioClient.removeObject(
          RemoveObjectArgs.builder()
              .bucket(bucket)
              .object(fileId)
              .build()
      );
      return ResultVo.success();
    } catch (Exception e) {
      return ResultVo.failed("文件删除失败");
    }
  }

  /**
   * 文件下载
   */
  public void downloadFile(String fileId, String bucket, HttpServletResponse response) {
    try {
      InputStream inputStream = minioClient.getObject(
          GetObjectArgs.builder()
              .bucket(bucket)
              .object(fileId)
              .build()
      );

      OutputStream outputStream = response.getOutputStream();

      // 设置响应头，告诉浏览器以附件形式下载
      response.setContentType("application/octet-stream");
      response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileId, StandardCharsets.UTF_8));

      byte[] buffer = new byte[1024];
      int bytesRead;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
      outputStream.flush();
    } catch (Exception e) {
      System.out.println("1111");
      // 异常处理
      throw new RuntimeException("文件下载失败", e);
    }
  }
}
