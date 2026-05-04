package com.example.taskmanagerback.app.impl.comment;

import com.example.taskmanagerback.adapter.out.repository.minio.FileRepo;
import com.example.taskmanagerback.app.api.in.comment.DeleteCommentFile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeleteCommentFileImpl implements DeleteCommentFile {
    FileRepo fileRepo;

    @Override
    public void execute(String commentId, String filename) {
        fileRepo.deleteCommentFile(commentId, filename);
    }
}
