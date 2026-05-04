package com.example.taskmanagerback.app.api.in.project;

import com.example.taskmanagerback.adapter.out.repository.minio.File;

import java.util.List;

public interface GetAllProjectsFiles {
    List<File> execute(String projectId);
}
