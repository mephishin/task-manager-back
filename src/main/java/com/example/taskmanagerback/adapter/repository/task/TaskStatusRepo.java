package com.example.taskmanagerback.adapter.repository.task;

import com.example.taskmanagerback.model.task.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskStatusRepo extends JpaRepository<TaskStatus, String> {
    Optional<TaskStatus> findByValue(String value);
}
