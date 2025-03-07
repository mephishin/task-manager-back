package com.example.taskmanagerback.app.impl;

import com.example.taskmanagerback.adapter.repository.ProjectRepo;
import com.example.taskmanagerback.adapter.repository.TaskRepo;
import com.example.taskmanagerback.app.api.GetTasksByProject;
import com.example.taskmanagerback.model.task.Task;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetTasksByProjectImpl implements GetTasksByProject {
    TaskRepo taskRepo;
    ProjectRepo projectRepo;

    @Override
    @Transactional
    public List<Task> execute(String projectName) {
        var project = projectRepo.findByName(projectName).orElseThrow();

        return taskRepo.findAllByProject(project);
    }
}
