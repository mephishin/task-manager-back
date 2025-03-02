package com.example.taskmanagerback.app.impl;

import com.example.taskmanagerback.adapter.repository.ProjectRepository;
import com.example.taskmanagerback.app.api.GetAllProjects;
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
    ProjectRepository projectRepository;

    @Override
    public List<Project> execute() {
        return projectRepository.findAll();
    }
}
