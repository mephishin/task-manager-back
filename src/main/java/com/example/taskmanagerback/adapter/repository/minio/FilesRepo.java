package com.example.taskmanagerback.adapter.repository.minio;

import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FilesRepo {
    private final MinioClient minioClient;
    @Value("${minio.bucket.task-files}")
    private String minioTaskFilesBucket;
    @Value("${minio.bucket.project-files}")
    private String minioProjectFilesBucket;

    public void saveCommentFile(String taskKey, String commentId, File file) {
        String objectKey = getCommentsFileKey(taskKey, commentId, file.name());

        save(minioTaskFilesBucket, file, objectKey);
    }

    public void saveProjectFile(String projectId, File file) {
        String objectKey = getProjectsFileKey(projectId, file.name());

        save(minioProjectFilesBucket,file, objectKey);
    }

    public List<File> getAllCommentsFiles(String taskKey, String commentId) {
        return getAllFilesByPrefix(minioTaskFilesBucket,getCommentsFilePrefix(taskKey, commentId));
    }

    public List<File> getAllProjectsFiles(String projectId) {
        return getAllFilesByPrefix(minioProjectFilesBucket, getProjectsFilePrefix(projectId));
    }

    private List<File> getAllFilesByPrefix(String bucket, String prefix) {
        try {
            var results = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(bucket)
                    .prefix(prefix)
                    .build());

            var listOfFiles = new ArrayList<File>();

            for (Result<Item> result : results) {
                var objectKey = result.get().objectName();

                var statObjectResponse = minioClient.statObject(StatObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectKey)
                        .build());

                listOfFiles.add(new File(
                        getCommentsFileNameFromKey(objectKey),
                        minioClient.getObject(GetObjectArgs.builder()
                                        .bucket(bucket)
                                        .object(objectKey)
                                        .build())
                                .readAllBytes(),
                        statObjectResponse.contentType()
                ));
            }

            return listOfFiles;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void save(String bucket, File file, String objectKey) {
        try (var bais = new ByteArrayInputStream(file.bytes())) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectKey)
                            .stream(bais, bais.available(), -1)
                            .contentType(getMimeType(file))
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getCommentsFileKey(String taskKey, String commentId, String fileName) {
        return String.join("/", getCommentsFilePrefix(taskKey, commentId), fileName);
    }

    private String getCommentsFilePrefix(String taskKey, String commentId) {
        return String.join("/", taskKey, commentId) + "/";
    }

    private String getProjectsFilePrefix(String projectId) {
        return projectId + "/";
    }

    private String getProjectsFileKey(String projectId, String fileName) {
        return String.join("/", projectId, fileName);
    }

    private String getCommentsFileNameFromKey(String key) {
        var parts = key.split("/");
        return parts[parts.length - 1];
    }

    private String getMimeType(File file) {
        try {
            return Files.probeContentType(Paths.get(file.name()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}