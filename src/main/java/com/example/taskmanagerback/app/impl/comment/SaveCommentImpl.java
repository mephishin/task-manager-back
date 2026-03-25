package com.example.taskmanagerback.app.impl.comment;

import com.example.taskmanagerback.adapter.repository.postgres.task.TaskRepo;
import com.example.taskmanagerback.app.api.comment.SaveComment;
import com.example.taskmanagerback.model.task.TaskComment;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SaveCommentImpl implements SaveComment {
    TaskRepo taskRepo;

    @Override
    @Transactional
    public void execute(String taskKey, TaskComment taskComment) {
        var task = taskRepo.findById(taskKey)
                .orElseThrow(() -> new RuntimeException("Task not found with key: " + taskKey));
        task.getTaskComments().add(taskComment);
    }
}
