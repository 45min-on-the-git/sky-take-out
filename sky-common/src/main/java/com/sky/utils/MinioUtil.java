package com.sky.utils;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

//该类替代了AilOssUtil类，用于对象存储

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class MinioUtil {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private MinioClient minioClient;

    public String upload(MultipartFile file) {
        String path = "";
        try {
            //建立minio客户端
            minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKeyId, accessKeySecret)
                    .build();

            //存储文件
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getOriginalFilename())
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            //获取文件预览地址
            log.info("文件名:{}",file.getOriginalFilename());
            path = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(file.getOriginalFilename())
                    .method(Method.GET)
                    .build());
            log.info("文件预览地址:{}",path);
        } catch (Exception e) {
            log.error("文件上传失败：{}",e.getMessage());
        }
        return path;
    }
}
