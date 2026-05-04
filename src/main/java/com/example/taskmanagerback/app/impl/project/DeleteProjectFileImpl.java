package com.example.taskmanagerback.app.impl.project;

import com.example.taskmanagerback.adapter.out.repository.minio.FileRepo;
import com.example.taskmanagerback.app.api.in.project.DeleteProjectFile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeleteProjectFileImpl implements DeleteProjectFile {
    FileRepo fileRepo;

    @Override
    public void execute(String projectId, String filename) {
        fileRepo.deleteProjectFile(projectId, filename);
    }
}
