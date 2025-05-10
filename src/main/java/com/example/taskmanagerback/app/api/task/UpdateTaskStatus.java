package com.example.taskmanagerback.app.api.task;

import com.example.taskmanagerback.model.task.constants.TaskStatus;

public interface UpdateTaskStatus {
    void execute(String key, TaskStatus taskStatus);
}
