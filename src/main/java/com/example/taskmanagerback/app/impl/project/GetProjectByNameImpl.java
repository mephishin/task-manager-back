package com.example.taskmanagerback.app.impl.project;

import com.example.taskmanagerback.adapter.repository.postgres.project.ProjectRepo;
import com.example.taskmanagerback.app.api.project.GetProjectByName;
import com.example.taskmanagerback.model.project.Project;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GetProjectByNameImpl implements GetProjectByName {
    ProjectRepo projectRepo;

    @Override
    public Project execute(String name) {
        return projectRepo.findByName(name).orElseThrow();
    }
}
