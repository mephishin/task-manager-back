package com.example.taskmanagerback.adapter.in.project;

import com.example.taskmanagerback.adapter.in.project.dto.CreateProjectDto;
import com.example.taskmanagerback.adapter.in.project.dto.ProjectDto;
import com.example.taskmanagerback.app.api.out.postgres.UsersRepo;
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
    public abstract ProjectDto.ParticipantDto projectToProjectDto(Users users);

    @Mapping(target = "participants", qualifiedByName = "addLeaderToParticipants", source = "leader")
    public abstract Project projectDtoToProject(CreateProjectDto createProjectDto);

    @Named("addLeaderToParticipants")
    protected List<Users> addLeaderToParticipants(String leader) {
        return usersRepo.findAllById(List.of(leader));
    }
}
