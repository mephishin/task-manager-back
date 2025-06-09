package com.example.taskmanagerback.app.api.task;

import com.example.taskmanagerback.model.task.constants.TaskStatus;

import java.util.List;

public interface GetAllowedTaskStatuses {
    List<TaskStatus> execute(String taskStatusId);
}
