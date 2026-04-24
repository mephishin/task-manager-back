package com.example.taskmanagerback.app.api.task;

import com.example.taskmanagerback.model.users.Users;
import com.example.taskmanagerback.model.task.Task;

public interface CreateTask {
    String execute(Task task, Users users);
}
