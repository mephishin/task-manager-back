package com.example.taskmanagerback.app.impl.comment;

import com.example.taskmanagerback.adapter.repository.minio.File;
import com.example.taskmanagerback.adapter.repository.minio.FileRepo;
import com.example.taskmanagerback.adapter.repository.postgres.task.TaskCommentRepo;
import com.example.taskmanagerback.app.api.comment.UpdateComment;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateCommentImpl implements UpdateComment {
    TaskCommentRepo taskCommentRepo;
    FileRepo fileRepo;

    @Override
    @Transactional
    public void execute(String commentId, String text, String username, List<File> files) {
        var comment = taskCommentRepo.findById(commentId).orElseThrow();

        comment.setText(text);

        if (nonNull(files)) {
            fileRepo.saveCommentFiles(commentId, files);
        }
    }
}
