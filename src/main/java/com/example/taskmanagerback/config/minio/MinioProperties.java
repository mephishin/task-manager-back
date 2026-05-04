package com.example.taskmanagerback.config.minio;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
public record MinioProperties(
        String url,
        String port,
        BucketProperties bucket,
        String accessKey,
        String secretKey
) {
    public record BucketProperties(
            String taskFiles,
            String projectFiles,
            String commentFiles
    ) {
    }
}
