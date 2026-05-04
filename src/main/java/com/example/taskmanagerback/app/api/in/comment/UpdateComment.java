package com.example.taskmanagerback.app.api.in.comment;

import com.example.taskmanagerback.adapter.out.repository.minio.File;

import java.util.List;

public interface UpdateComment {
    void execute(String commentId, String text, String username, List<File> files);
}
