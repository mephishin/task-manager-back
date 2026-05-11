package com.example.taskmanagerback.app.impl.project;

import com.example.taskmanagerback.adapter.out.repository.postgres.project.ProjectRepo;
import com.example.taskmanagerback.adapter.out.repository.postgres.users.UsersJpaRepo;
import com.example.taskmanagerback.app.api.in.project.CreateProject;
import com.example.taskmanagerback.model.project.Project;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateProjectImpl implements CreateProject {
    ProjectRepo projectRepo;
    UsersJpaRepo usersJpaRepo;

    @Override
    @Transactional
    public void execute(Project project) {
        project.setStatusFlow("{\"TO_DO\":[\"IN_PROGRESS\",\"DONE\"],\"IN_PROGRESS\":[\"DONE\",\"TO_DO\"],\"DONE\":[\"TO_DO\"]}");

        var savedProject = projectRepo.save(project);
        usersJpaRepo.findById(project.getParticipants().get(0).getId()).orElseThrow().setProject(savedProject);
    }
}
