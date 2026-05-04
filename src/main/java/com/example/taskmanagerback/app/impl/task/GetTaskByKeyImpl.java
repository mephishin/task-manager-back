package com.example.taskmanagerback.app.impl.task;

import com.example.taskmanagerback.adapter.out.repository.postgres.task.TaskRepo;
import com.example.taskmanagerback.app.api.in.task.GetTaskByKey;
import com.example.taskmanagerback.app.api.out.postgres.UsersRepo;
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
    UsersRepo usersRepo;

    @Override
    public Task execute(String key) {
        var task = taskRepo.findById(key).orElseThrow();

        task.setAssignee(usersRepo.findById(task.getAssignee().getId()).orElseThrow());
        task.setReporter(usersRepo.findById(task.getReporter().getId()).orElseThrow());

        return task;
    }
}
