package com.example.taskmanagerback.app.impl.project;

import com.example.taskmanagerback.adapter.repository.minio.File;
import com.example.taskmanagerback.adapter.repository.minio.FilesRepo;
import com.example.taskmanagerback.app.api.project.GetAllProjectsFiles;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAllProjectsFilesImpl implements GetAllProjectsFiles {
    FilesRepo filesRepo;

    @Override
    public List<File> execute(String projectId) {
        filesRepo.saveProjectFile(projectId, new File("new.png", "rte".getBytes(), null));

        return filesRepo.getAllProjectsFiles(projectId);
    }
}
