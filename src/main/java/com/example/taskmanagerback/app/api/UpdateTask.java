package com.example.taskmanagerback.app.api;

import com.example.taskmanagerback.adapter.in.task.dto.TaskDto;
import com.example.taskmanagerback.model.task.Task;

public interface UpdateTask {
    Task execute(TaskDto taskDto);
}
