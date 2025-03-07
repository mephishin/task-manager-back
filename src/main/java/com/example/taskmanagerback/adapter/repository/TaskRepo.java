package com.example.taskmanagerback.adapter.repository;

import com.example.taskmanagerback.model.project.Project;
import com.example.taskmanagerback.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, String> {
    List<Task> findAllByProject(Project project);
}
