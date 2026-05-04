package com.example.taskmanagerback.app.api.in.comment;

import com.example.taskmanagerback.model.task.TaskComment;

import java.util.List;

public interface GetTaskComments {
    List<TaskComment> execute(String taskKey);
}
