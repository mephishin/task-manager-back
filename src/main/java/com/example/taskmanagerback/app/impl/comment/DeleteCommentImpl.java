package com.example.taskmanagerback.app.impl.comment;

import com.example.taskmanagerback.adapter.out.repository.minio.FileRepo;
import com.example.taskmanagerback.adapter.out.repository.postgres.task.TaskCommentRepo;
import com.example.taskmanagerback.app.api.in.comment.DeleteComment;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class DeleteCommentImpl implements DeleteComment {
    TaskCommentRepo taskCommentRepo;
    FileRepo fileRepo;

    @Override
    public void execute(String commentId) {
        taskCommentRepo.deleteById(commentId);
        fileRepo.deleteAllCommentFiles(commentId);
    }
}
