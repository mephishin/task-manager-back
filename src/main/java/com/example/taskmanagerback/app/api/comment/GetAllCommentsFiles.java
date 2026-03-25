package com.example.taskmanagerback.app.api.comment;

import com.example.taskmanagerback.adapter.repository.minio.File;

import java.util.List;
import java.util.Map;

public interface GetAllCommentsFiles {
    Map<String, List<File>> execute(List<String> commentIds);
}
