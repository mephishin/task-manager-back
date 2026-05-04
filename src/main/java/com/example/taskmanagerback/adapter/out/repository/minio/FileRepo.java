package com.example.taskmanagerback.adapter.out.repository.minio;

import com.example.taskmanagerback.config.minio.MinioProperties;
import io.minio.*;
import io.minio.messages.Item;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileRepo {
    MinioClient minioClient;
    MinioProperties minioProperties;

    public void saveProjectFile(String projectId, List<File> files) {
        files.forEach(file -> save(
                minioProperties.bucket().projectFiles(),
                file,
                getProjectsFileKey(projectId, file.name())
        ));
    }

    public List<File> getAllProjectFiles(String projectId) {
        return getAllFilesByPrefix(minioProperties.bucket().projectFiles(), getProjectFilePrefix(projectId));
    }

    public void deleteProjectFile(String projectId, String filename) {
        delete(minioProperties.bucket().projectFiles(), getProjectsFileKey(projectId, filename));
    }

    public void deleteCommentFile(String commentId, String filename) {
        delete(minioProperties.bucket().commentFiles(), getCommentFileKey(commentId, filename));
    }

    public void deleteAllCommentFiles(String commentId) {
        deleteAllFilesByPrefix(minioProperties.bucket().commentFiles(), getCommentFilePrefix(commentId));
    }

    public void saveCommentFiles(String commentId, List<File> files) {
        files.forEach(file -> save(
                minioProperties.bucket().commentFiles(),
                file,
                getCommentFileKey(commentId, file.name())
        ));
    }

    public List<File> getAllCommentFiles(String commentId) {
        return getAllFilesByPrefix(minioProperties.bucket().commentFiles(), getCommentFilePrefix(commentId));
    }

    private void deleteAllFilesByPrefix(String bucket, String prefix) {
        try {
            var results = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(bucket)
                    .prefix(prefix)
                    .build());

            for (Result<Item> result : results) {
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucket)
                                .object(result.get().objectName())
                                .build()
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
                        getFileNameFromKey(objectKey),
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

    private void delete(String bucket, String objectKey) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectKey)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getCommentFileKey(String commentId, String fileName) {
        return String.join("/", getCommentFilePrefix(commentId), fileName);
    }

    private String getCommentFilePrefix(String commentId) {
        return String.join("/", commentId) + "/";
    }

    private String getProjectFilePrefix(String projectId) {
        return projectId + "/";
    }

    private String getProjectsFileKey(String projectId, String fileName) {
        return String.join("/", projectId, fileName);
    }

    private String getFileNameFromKey(String key) {
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