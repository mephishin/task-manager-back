package com.example.taskmanagerback.app.api.in.comment;

import com.example.taskmanagerback.adapter.out.repository.minio.File;

import java.util.List;

public interface SaveComment {
    void execute(String taskKey, String text, String userId, List<File> files);
}
