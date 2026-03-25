package com.example.taskmanagerback.app.impl.task;

import com.example.taskmanagerback.adapter.repository.postgres.project.ProjectRepo;
import com.example.taskmanagerback.adapter.repository.postgres.task.TaskRepo;
import com.example.taskmanagerback.app.api.task.GetTasksByProject;
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
    public List<Task> execute(String projectId) {
        var project = projectRepo.findById(projectId).orElseThrow();

        return taskRepo.findAllByProject(project);
    }
}
