package com.example.taskmanagerback.app.impl;

import com.example.taskmanagerback.adapter.repository.ProjectRepository;
import com.example.taskmanagerback.adapter.repository.TaskRepository;
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
    TaskRepository taskRepository;
    ProjectRepository projectRepository;

    @Override
    @Transactional
    public List<Task> execute(String projectName) {
        var project = projectRepository.findByName(projectName).orElseThrow();

        return taskRepository.findAllByProject(project);
    }
}
