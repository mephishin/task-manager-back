package com.example.taskmanagerback.app.api.project;

import com.example.taskmanagerback.adapter.repository.minio.File;

public interface SaveProjectFile {
    void execute(String projectId, File file);
}
