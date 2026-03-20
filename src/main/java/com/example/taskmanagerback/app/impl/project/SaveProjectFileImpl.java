package com.example.taskmanagerback.app.impl.project;

import com.example.taskmanagerback.adapter.repository.minio.File;
import com.example.taskmanagerback.adapter.repository.minio.FilesRepo;
import com.example.taskmanagerback.app.api.project.SaveProjectFile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SaveProjectFileImpl implements SaveProjectFile {
    FilesRepo filesRepo;

    @Override
    public void execute(String projectId, File file) {
        filesRepo.saveProjectFile(projectId, file);
    }
}
