package com.example.taskmanagerback.app.api;

import com.example.taskmanagerback.model.task.Task;

import java.util.List;

public interface GetTasksByProject {
    List<Task> execute(String projectName);
}
