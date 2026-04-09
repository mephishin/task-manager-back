package com.example.taskmanagerback.app.api.comment;

import com.example.taskmanagerback.adapter.repository.minio.File;

import java.util.List;

public interface UpdateComment {
    void execute(String commentId, String text, String username, List<File> files);
}
