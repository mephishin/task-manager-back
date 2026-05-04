package com.example.taskmanagerback.config.minio;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MinioConfig {
    MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        try {
            var minioClient = MinioClient.builder()
                    .endpoint(minioProperties.url() + ":" + minioProperties.port())
                    .credentials(minioProperties.accessKey(), minioProperties.secretKey())
                    .build();

            var isTaskFilesBucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.bucket().taskFiles()).build());
            var isProjectFilesBucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.bucket().projectFiles()).build());
            var isCommentFilesBucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.bucket().commentFiles()).build());

            if (!isTaskFilesBucketExists) {
                throw new RuntimeException("No bucket: " + minioProperties.bucket().taskFiles());
            } else if (!isProjectFilesBucketExists) {
                throw new RuntimeException("No bucket: " + minioProperties.bucket().projectFiles());
            } else if (!isCommentFilesBucketExists) {
                throw new RuntimeException("No bucket: " + minioProperties.bucket().commentFiles());
            }

            return minioClient;
        } catch (Exception e) {
            log.error("Failed to configure MinioClient bean: {}", e.getMessage());
        }
        return null;
    }
}