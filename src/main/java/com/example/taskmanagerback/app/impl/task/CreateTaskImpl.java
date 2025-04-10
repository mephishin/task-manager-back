package com.example.taskmanagerback.app.impl.task;

import com.example.taskmanagerback.adapter.repository.task.TaskRepo;
import com.example.taskmanagerback.app.api.task.CreateTask;
import com.example.taskmanagerback.app.api.task.GetCurrentTaskKeyByProject;
import com.example.taskmanagerback.model.participant.Participant;
import com.example.taskmanagerback.model.task.Task;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateTaskImpl implements CreateTask {
    TaskRepo taskRepo;
    GetCurrentTaskKeyByProject getCurrentTaskKeyByProject;

    @Override
    public Task execute(Task task, Participant participant) {
        task.setKey(getCurrentTaskKeyByProject.execute(task.getProject().getName()));
        task.setReporter(participant);
        return taskRepo.save(task);
    }
}
