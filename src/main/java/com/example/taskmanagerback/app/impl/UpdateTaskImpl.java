package com.example.taskmanagerback.app.impl;

import com.example.taskmanagerback.adapter.in.task.dto.TaskDto;
import com.example.taskmanagerback.adapter.repository.ProjectRepo;
import com.example.taskmanagerback.adapter.repository.TaskRepo;
import com.example.taskmanagerback.adapter.repository.TaskStatusRepo;
import com.example.taskmanagerback.adapter.repository.TaskTypeRepo;
import com.example.taskmanagerback.app.api.UpdateTask;
import com.example.taskmanagerback.model.task.Task;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateTaskImpl implements UpdateTask {
    TaskRepo taskRepo;
    ProjectRepo projectRepo;
    TaskStatusRepo taskStatusRepo;
    TaskTypeRepo taskTypeRepo;

    @Override
    @Transactional
    public Task execute(TaskDto taskDto) {
        var taskStatus = taskStatusRepo.findByValue(taskDto.getStatus()).orElseThrow();
        var taskType = taskTypeRepo.findByValue(taskDto.getType()).orElseThrow();
        var task = taskRepo.findById(taskDto.getKey()).orElseThrow();
        var project = projectRepo.findByName(taskDto.getProject()).orElseThrow();

        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskStatus);
        task.setType(taskType);
        task.setProject(project);

        return task;
    }
}
