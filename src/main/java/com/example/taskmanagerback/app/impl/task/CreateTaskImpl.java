package com.example.taskmanagerback.app.impl.task;

import com.example.taskmanagerback.adapter.repository.postgres.task.TaskRepo;
import com.example.taskmanagerback.adapter.repository.postgres.task.TaskTimeRepo;
import com.example.taskmanagerback.app.api.task.CreateTask;
import com.example.taskmanagerback.app.api.task.status.GetProjectStatuses;
import com.example.taskmanagerback.model.task.Task;
import com.example.taskmanagerback.model.task.TaskTime;
import com.example.taskmanagerback.model.users.Users;
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
    GetProjectStatuses getProjectStatuses;

    @Override
    @Transactional
    public Task execute(Task task, Users users) {
        var currentProjectKey = getCurrentProjectKey(task);
        log.info("Creating a task with key: {}", currentProjectKey);

        task.setStatus(getProjectStatuses.execute(task.getProject().getKey()).get(0));
        task.setKey(currentProjectKey);
        task.setReporter(users);

        var createdTask = taskRepo.save(task);

        var taskTime = createTaskTime(createdTask);

        createdTask.setTaskTime(taskTime);

        return createdTask;
    }

    private String getCurrentProjectKey(Task task) {
        var taskPrefix = task.getProject().getTaskPrefix();
        return taskPrefix+ "-" + (taskRepo.getCurrentTaskNumberByTaskPrefix(taskPrefix).orElse(0L) + 1);
    }

    private TaskTime createTaskTime(Task task) {
        return taskTimeRepo.save(
                new TaskTime()
                        .setCreated(Instant.now())
                        .setTaskKey(task.getKey())
        );
    }
}
