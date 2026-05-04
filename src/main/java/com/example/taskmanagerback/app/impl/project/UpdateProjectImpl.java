package com.example.taskmanagerback.app.impl.project;

import com.example.taskmanagerback.adapter.out.repository.minio.File;
import com.example.taskmanagerback.adapter.out.repository.minio.FileRepo;
import com.example.taskmanagerback.adapter.out.repository.postgres.project.ProjectRepo;
import com.example.taskmanagerback.app.api.in.project.UpdateProject;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateProjectImpl implements UpdateProject {
    FileRepo fileRepo;
    ProjectRepo projectRepo;

    @Override
    @Transactional
    public void execute(String projectId, String description, List<File> files) {
        var project = projectRepo.findById(projectId).orElseThrow();
        project.setDescription(description);
        if (nonNull(files)) fileRepo.saveProjectFile(projectId, files);
    }
}
