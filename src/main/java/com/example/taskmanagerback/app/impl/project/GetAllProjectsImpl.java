package com.example.taskmanagerback.app.impl.project;

import com.example.taskmanagerback.adapter.repository.project.ProjectRepo;
import com.example.taskmanagerback.app.api.project.GetAllProjects;
import com.example.taskmanagerback.model.project.Project;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAllProjectsImpl implements GetAllProjects {
    ProjectRepo projectRepo;

    @Override
    public List<Project> execute() {
        return projectRepo.findAll();
    }
}
