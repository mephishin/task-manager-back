package com.example.taskmanagerback.app.impl.comment;

import com.example.taskmanagerback.adapter.out.repository.minio.File;
import com.example.taskmanagerback.adapter.out.repository.minio.FileRepo;
import com.example.taskmanagerback.adapter.out.repository.postgres.task.TaskCommentRepo;
import com.example.taskmanagerback.adapter.out.repository.postgres.task.TaskRepo;
import com.example.taskmanagerback.app.api.in.comment.SaveComment;
import com.example.taskmanagerback.app.api.out.postgres.UsersRepo;
import com.example.taskmanagerback.model.task.TaskComment;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SaveCommentImpl implements SaveComment {
    TaskRepo taskRepo;
    FileRepo fileRepo;
    UsersRepo usersRepo;
    TaskCommentRepo taskCommentRepo;

    @Override
    @Transactional
    public void execute(String taskKey, String text, String userId, List<File> files) {
        var task = taskRepo.findById(taskKey)
                .orElseThrow(() -> new RuntimeException("Task not found with key: " + taskKey));

        var comment = taskCommentRepo.save(new TaskComment()
                .setText(text)
                .setAuthor(usersRepo.findById(userId).orElseThrow())
                .setCreated(Instant.now()));

        task.getTaskComments().add(comment);

        if (nonNull(files)) {
            fileRepo.saveCommentFiles(comment.getId(), files);
        }
    }
}
