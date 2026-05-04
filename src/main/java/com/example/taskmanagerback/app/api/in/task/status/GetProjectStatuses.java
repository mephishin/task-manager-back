package com.example.taskmanagerback.app.api.in.task.status;

import com.example.taskmanagerback.model.task.constants.TaskStatus;

import java.util.List;

public interface GetProjectStatuses {
    List<TaskStatus> execute(String projectId);
}
