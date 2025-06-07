package com.example.taskmanagerback.adapter.in.project;

import com.example.taskmanagerback.adapter.in.project.dto.ProjectDto;
import com.example.taskmanagerback.app.api.project.GetAllProjects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {
    GetAllProjects getAllProjects;
    ProjectMapper projectMapper;

    @GetMapping("/projects")
    public List<ProjectDto> getAllProjects() {
        log.info("Requested all projects");
        return getAllProjects.execute().stream()
                .map(projectMapper::projectToProjectDto)
                .toList();
    }
}
