package com.example.taskmanagerback.app.impl.task;

import com.example.taskmanagerback.adapter.repository.postgres.task.TaskRepo;
import com.example.taskmanagerback.app.api.task.GetTaskByKey;
import com.example.taskmanagerback.model.task.Task;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetTaskByKeyImpl implements GetTaskByKey {
    TaskRepo taskRepo;

    @Override
    public Task execute(String key) {
        return taskRepo.findById(key).orElseThrow();
    }
}
