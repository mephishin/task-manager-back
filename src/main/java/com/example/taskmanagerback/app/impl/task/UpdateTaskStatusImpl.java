package com.example.taskmanagerback.app.impl.task;

import com.example.taskmanagerback.adapter.repository.task.TaskRepo;
import com.example.taskmanagerback.app.api.task.UpdateTaskStatus;
import com.example.taskmanagerback.model.task.constants.TaskStatus;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UpdateTaskStatusImpl implements UpdateTaskStatus {
    TaskRepo taskRepo;

    @Override
    @Transactional
    public void execute(String key, TaskStatus taskStatus) {
        var task = taskRepo.findById(key).orElseThrow();

        task.setStatus(taskStatus);
    }
}
