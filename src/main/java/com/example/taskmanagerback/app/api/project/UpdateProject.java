package com.example.taskmanagerback.app.api.project;

import com.example.taskmanagerback.adapter.repository.minio.File;

import java.util.List;

public interface UpdateProject {
    void execute(String projectId, String description, List<File> files);
}
