package com.example.taskmanagerback.adapter.repository.postgres.project;

import com.example.taskmanagerback.model.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project, String> {
    Optional<Project> findByName(String projectName);
}
