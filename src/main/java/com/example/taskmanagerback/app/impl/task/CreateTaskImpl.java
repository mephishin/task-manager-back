package com.example.taskmanagerback.app.impl.task;

import com.example.taskmanagerback.adapter.repository.task.TaskRepo;
import com.example.taskmanagerback.adapter.repository.task.TaskTimeRepo;
import com.example.taskmanagerback.app.api.task.CreateTask;
import com.example.taskmanagerback.app.api.task.GetCurrentTaskKeyByProject;
import com.example.taskmanagerback.model.participant.Participant;
import com.example.taskmanagerback.model.task.Task;
import com.example.taskmanagerback.model.task.TaskTime;
import com.example.taskmanagerback.model.task.constants.TaskStatus;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CreateTaskImpl implements CreateTask {
    TaskRepo taskRepo;
    TaskTimeRepo taskTimeRepo;
    GetCurrentTaskKeyByProject getCurrentTaskKeyByProject;

    @Override
    @Transactional
    public Task execute(Task task, Participant participant) {
        var currentProjectKey = getCurrentTaskKeyByProject.execute(task.getProject().getName());
        log.info("Creating a task with key: {}", currentProjectKey);

        task.setStatus(TaskStatus.TO_DO);
        task.setKey(currentProjectKey);
        task.setReporter(participant);

        var createdTask = taskRepo.save(task);

        var taskTime = createTaskTime(createdTask);

        createdTask.setTaskTime(taskTime);

        return createdTask;
    }

    private TaskTime createTaskTime(Task task) {
        return taskTimeRepo.save(
                new TaskTime()
                        .setCreated(Instant.now())
                        .setTaskKey(task.getKey())
        );
    }
}
