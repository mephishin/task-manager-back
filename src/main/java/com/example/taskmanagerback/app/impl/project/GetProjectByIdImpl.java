package com.example.taskmanagerback.app.impl.project;

import com.example.taskmanagerback.adapter.out.repository.postgres.project.ProjectRepo;
import com.example.taskmanagerback.app.api.in.project.GetProjectById;
import com.example.taskmanagerback.app.api.out.postgres.UsersRepo;
import com.example.taskmanagerback.model.project.Project;
import com.example.taskmanagerback.model.users.Users;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetProjectByIdImpl implements GetProjectById {
    UsersRepo usersRepo;
    ProjectRepo projectRepo;

    @Override
    @Transactional
    public Project execute(String id) {
        var project = projectRepo.findById(id).orElseThrow();

        project.setParticipants(usersRepo.findAllById(project.getParticipants().stream().map(Users::getId).toList()));

        return project;
    }
}
