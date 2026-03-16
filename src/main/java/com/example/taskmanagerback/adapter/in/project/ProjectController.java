package com.example.taskmanagerback.adapter.in.project;

import com.example.taskmanagerback.adapter.in.project.dto.CreateProjectDto;
import com.example.taskmanagerback.adapter.in.project.dto.ProjectDto;
import com.example.taskmanagerback.app.api.project.GetAllProjects;
import com.example.taskmanagerback.app.api.security.GetAuthParticipant;
import com.example.taskmanagerback.app.api.security.GetJwtAuthenticationToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {
    GetAllProjects getAllProjects;
    ProjectMapper projectMapper;
    GetJwtAuthenticationToken getJwtAuthenticationToken;
    GetAuthParticipant getAuthParticipant;

    @GetMapping("/projects")
    public List<ProjectDto> getAllProjects() {
        log.info("Requested all projects");
        return getAllProjects.execute().stream()
                .map(projectMapper::projectToProjectDto)
                .toList();
    }

    @GetMapping(path = "/project", params = "filter=auth")
    public ProjectDto getProjectByAuthParticipant(Principal principal) {
        log.info("Requested project by auth participant {}", principal.getName());

        return projectMapper.projectToProjectDto(
                getAuthParticipant.execute(getJwtAuthenticationToken.execute()).getProject()
        );
    }

    @PostMapping(path = "/project")
    @PreAuthorize("hasAnyAuthority('task-manager_leader', 'task-manager_admin')")
    public ProjectDto createProject(@RequestBody CreateProjectDto createProjectDto) {
        log.info("Requested project by auth participant");

        return projectMapper.projectToProjectDto(
                getAuthParticipant.execute(getJwtAuthenticationToken.execute()).getProject()
        );
    }
}
