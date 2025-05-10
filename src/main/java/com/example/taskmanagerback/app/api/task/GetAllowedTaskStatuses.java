package com.example.taskmanagerback.app.api.task;

import java.util.List;

public interface GetAllowedTaskStatuses {
    List<String> execute(String taskStatusId);
}
