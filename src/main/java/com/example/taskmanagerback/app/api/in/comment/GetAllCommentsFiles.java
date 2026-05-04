package com.example.taskmanagerback.app.api.in.comment;

import com.example.taskmanagerback.adapter.out.repository.minio.File;

import java.util.List;
import java.util.Map;

public interface GetAllCommentsFiles {
    Map<String, List<File>> execute(List<String> commentIds);
}
