package com.example.taskmanagerback.adapter.repository.postgres.project;

import com.example.taskmanagerback.model.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepo extends JpaRepository<Project, String> {
    Optional<Project> findByName(String projectName);
}
