package com.example.taskmanagerback.adapter.in.project;

import com.example.taskmanagerback.adapter.in.project.dto.CreateProjectDto;
import com.example.taskmanagerback.adapter.in.project.dto.ProjectDto;
import com.example.taskmanagerback.adapter.repository.postgres.task.UsersRepo;
import com.example.taskmanagerback.model.project.Project;
import com.example.taskmanagerback.model.users.Users;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class ProjectMapper {
    @Autowired
    UsersRepo usersRepo;

    public abstract ProjectDto projectToProjectDto(Project project);

    @Mapping(target = "participants", qualifiedByName = "usernamesToParticipants", source = "participants")
    public abstract Project projectDtoToProject(CreateProjectDto createProjectDto);

    @Named("usernamesToParticipants")
    protected List<Users> usernamesToParticipants(List<String> usernames) {
        return usersRepo.findAllByUsernameIn(usernames);
    }
}
