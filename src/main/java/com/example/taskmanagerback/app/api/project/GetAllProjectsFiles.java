package com.example.taskmanagerback.app.api.project;

import com.example.taskmanagerback.adapter.repository.minio.File;

import java.util.List;

public interface GetAllProjectsFiles {
    List<File> execute(String projectId);
}
