package com.example.taskmanagerback.app.impl.comment;

import com.example.taskmanagerback.adapter.repository.minio.File;
import com.example.taskmanagerback.adapter.repository.minio.FileRepo;
import com.example.taskmanagerback.app.api.comment.GetAllCommentsFiles;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAllCommentsFilesImpl implements GetAllCommentsFiles {
    FileRepo fileRepo;

    @Override
    public Map<String, List<File>> execute(List<String> commentIds) {
        var commentsFiles = new HashMap<String, List<File>>();
        commentIds.forEach(id -> commentsFiles.put(id, fileRepo.getAllCommentFiles(id)));
        return commentsFiles;
    }
}
