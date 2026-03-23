package com.example.taskmanagerback.app.impl.project;

import com.example.taskmanagerback.adapter.repository.minio.File;
import com.example.taskmanagerback.adapter.repository.minio.FileRepo;
import com.example.taskmanagerback.app.api.project.SaveProjectFile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SaveProjectFileImpl implements SaveProjectFile {
    FileRepo fileRepo;

    @Override
    public void execute(String projectId, File file) {
        fileRepo.saveProjectFile(projectId, file);
    }
}
