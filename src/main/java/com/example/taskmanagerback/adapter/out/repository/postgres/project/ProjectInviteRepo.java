package com.example.taskmanagerback.adapter.out.repository.postgres.project;

import com.example.taskmanagerback.model.project.Project;
import com.example.taskmanagerback.model.project.ProjectInvite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectInviteRepo extends JpaRepository<ProjectInvite, String> {
    Optional<ProjectInvite> findByProjectAndUsed(Project project, Boolean used);
}
