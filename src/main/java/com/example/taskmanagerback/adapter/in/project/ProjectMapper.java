package com.example.taskmanagerback.adapter.in.project;

import com.example.taskmanagerback.adapter.in.project.dto.ProjectDto;
import com.example.taskmanagerback.model.project.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProjectMapper {
    public abstract ProjectDto projectToProjectDto(Project project);
}
