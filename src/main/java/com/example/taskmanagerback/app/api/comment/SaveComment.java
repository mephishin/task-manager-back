package com.example.taskmanagerback.app.api.comment;

import com.example.taskmanagerback.model.task.TaskComment;

public interface SaveComment {
    void execute(String taskKey, TaskComment taskComment);
}
