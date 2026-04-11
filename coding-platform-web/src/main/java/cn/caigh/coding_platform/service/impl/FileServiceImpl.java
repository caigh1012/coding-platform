package cn.caigh.coding_platform.service.impl;

import cn.caigh.coding_platform.pojo.vo.common.File;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.service.FileService;
import cn.hutool.core.util.IdUtil;
import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
  private final MinioClient minioClient;

  @Value("${minio.endpoint}")
  private String endpoint;

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
    System.out.println(fileId + bucket);
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
}
