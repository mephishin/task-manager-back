package com.example.taskmanagerback.app.impl.task;

import com.example.taskmanagerback.adapter.repository.postgres.task.TaskRepo;
import com.example.taskmanagerback.app.api.task.UpdateTask;
import com.example.taskmanagerback.model.task.Task;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateTaskImpl implements UpdateTask {
    TaskRepo taskRepo;

    @Override
    @Transactional
    public Task execute(Task newTask) {
        var task = taskRepo.findById(newTask.getKey()).orElseThrow();

        task.setName(newTask.getName());
        task.setDescription(newTask.getDescription());
        task.setAssignee(newTask.getAssignee());
        task.getTaskTime().setEdited(Instant.now());

        return task;
    }
}
