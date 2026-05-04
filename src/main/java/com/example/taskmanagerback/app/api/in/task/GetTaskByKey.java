package com.example.taskmanagerback.app.api.in.task;

import com.example.taskmanagerback.model.task.Task;

public interface GetTaskByKey {
    Task execute(String key);
}
