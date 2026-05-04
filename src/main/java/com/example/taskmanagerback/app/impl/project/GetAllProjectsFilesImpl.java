package com.example.taskmanagerback.app.impl.project;

import com.example.taskmanagerback.adapter.out.repository.minio.File;
import com.example.taskmanagerback.adapter.out.repository.minio.FileRepo;
import com.example.taskmanagerback.app.api.in.project.GetAllProjectsFiles;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAllProjectsFilesImpl implements GetAllProjectsFiles {
    FileRepo fileRepo;

    @Override
    public List<File> execute(String projectId) {
        return fileRepo.getAllProjectFiles(projectId);
    }
}
