package com.sky.config;

import com.sky.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {
    @Value("${minio.endpoint}")
    private String minioEndpoint;

    @Value("${minio.access-key}")
    private String minioAccessKey;

    @Value("${minio.secret-key}")
    private String minioSecretKey;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Bean
    public MinioUtil minioUtil() {
        return MinioUtil.builder()
                .endpoint(minioEndpoint)
                .accessKeyId(minioAccessKey)
                .accessKeySecret(minioSecretKey)
                .bucketName(bucketName)
                .build();
    }
}
