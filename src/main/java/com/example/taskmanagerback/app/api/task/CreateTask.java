package com.example.taskmanagerback.app.api.task;

import com.example.taskmanagerback.model.participant.Participant;
import com.example.taskmanagerback.model.task.Task;

public interface CreateTask {
    Task execute(Task task, Participant participant);
}
