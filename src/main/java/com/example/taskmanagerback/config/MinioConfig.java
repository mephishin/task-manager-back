package com.example.taskmanagerback.config;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MinioConfig {
    @Value("${minio.url}")
    private String minioUrl;
    @Value("${minio.port}")
    private String minioPort;
    @Value("${minio.bucket.task-files}")
    private String minioTaskFilesBucket;
    @Value("${minio.bucket.project-files}")
    private String minioProjectFilesBucket;
    @Value("${minio.bucket.comment-files}")
    private String minioCommentFilesBucket;
    @Value("${minio.accessKey}")
    private String minioAccessKey;
    @Value("${minio.secretKey}")
    private String minioSecretKey;

    @Bean
    public MinioClient minioClient() {
        try {
            var minioClient = MinioClient.builder()
                    .endpoint(minioUrl + ":" + minioPort)
                    .credentials(minioAccessKey, minioSecretKey)
                    .build();

            var isTaskFilesBucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioTaskFilesBucket).build());
            var isProjectFilesBucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioTaskFilesBucket).build());
            var isCommentFilesBucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioCommentFilesBucket).build());

            if (!isTaskFilesBucketExists) {
                throw new RuntimeException("No bucket: " + minioTaskFilesBucket);
            } else if (!isProjectFilesBucketExists) {
                throw new RuntimeException("No bucket: " + minioProjectFilesBucket);
            } else if (!isCommentFilesBucketExists) {
                throw new RuntimeException("No bucket: " + minioCommentFilesBucket);
            }

            return minioClient;
        } catch (Exception e) {
            log.error("Failed to configure MinioClient bean: {}", e.getMessage());
        }
        return null;
    }
}