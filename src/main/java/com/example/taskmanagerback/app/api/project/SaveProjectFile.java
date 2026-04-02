package com.example.taskmanagerback.app.api.project;

import com.example.taskmanagerback.adapter.repository.minio.File;

import java.util.List;

public interface SaveProjectFile {
    void execute(String projectId, List<File> files);
}
