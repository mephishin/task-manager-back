package com.example.taskmanagerback.adapter.out.repository.postgres.task;

import com.example.taskmanagerback.model.task.TaskTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTimeRepo extends JpaRepository<TaskTime, String> {
}
