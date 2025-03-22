package com.example.taskmanagerback.adapter.repository.task;

import com.example.taskmanagerback.model.task.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskTypeRepo extends JpaRepository<TaskType, String> {
    Optional<TaskType> findByValue(String value);
}
