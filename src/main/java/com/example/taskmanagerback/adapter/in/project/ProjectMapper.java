package com.example.taskmanagerback.adapter.in.project;

import com.example.taskmanagerback.adapter.in.project.dto.ProjectDto;
import com.example.taskmanagerback.model.project.Project;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectMapper {
    public ProjectDto projectToProjectDto(Project project) {
        return ProjectDto.builder()
                .key(project.getKey())
                .name(project.getName())
                .build();
    }
}
