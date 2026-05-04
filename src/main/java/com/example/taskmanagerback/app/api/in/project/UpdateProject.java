package com.example.taskmanagerback.app.api.in.project;

import com.example.taskmanagerback.adapter.out.repository.minio.File;

import java.util.List;

public interface UpdateProject {
    void execute(String projectId, String description, List<File> files);
}
