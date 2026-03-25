package com.example.taskmanagerback.adapter.repository.postgres.task;

import com.example.taskmanagerback.model.task.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCommentRepo extends JpaRepository<TaskComment, String> {
}
