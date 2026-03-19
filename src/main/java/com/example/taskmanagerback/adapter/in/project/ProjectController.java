package com.example.taskmanagerback.adapter.in.project;

import com.example.taskmanagerback.adapter.in.project.dto.CreateProjectDto;
import com.example.taskmanagerback.adapter.in.project.dto.ProjectDto;
import com.example.taskmanagerback.adapter.repository.minio.File;
import com.example.taskmanagerback.app.api.project.CreateProject;
import com.example.taskmanagerback.app.api.project.GetAllProjects;
import com.example.taskmanagerback.app.api.project.GetAllProjectsFiles;
import com.example.taskmanagerback.app.api.security.GetAuthUser;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("isAuthenticated()")
public class ProjectController {
    GetAllProjects getAllProjects;
    ProjectMapper projectMapper;
    GetJwtAuthenticationToken getJwtAuthenticationToken;
    GetAuthUser getAuthUser;
    CreateProject createProject;
    GetAllProjectsFiles getAllProjectsFiles;

    @GetMapping("/projects")
    public List<ProjectDto> getAllProjects() {
        log.info("Requested all projects");
        return getAllProjects.execute().stream()
                .map(projectMapper::projectToProjectDto)
                .toList();
    }

    @GetMapping("/projects/{projectId}/file")
    public List<File> getAllProjects(@PathVariable("projectId") String projectId) {
        log.info("Requested all projects files");
        return getAllProjectsFiles.execute(projectId);
    }

    @GetMapping(path = "/project", params = "filter=auth")
    public ProjectDto getProjectByAuthUser(Principal principal) {
        log.info("Requested project by auth participant {}", principal.getName());

        return projectMapper.projectToProjectDto(
                getAuthUser.execute(getJwtAuthenticationToken.execute()).getProject()
        );
    }

    @PostMapping(path = "/project")
    @PreAuthorize("hasAnyAuthority('task-manager_leader', 'task-manager_admin')")
    public ProjectDto createProject(@RequestBody CreateProjectDto createProjectDto) {
        log.info("Requested creating project by auth participant");

        return projectMapper.projectToProjectDto(
                createProject.execute(projectMapper.projectDtoToProject(createProjectDto)));
    }
}
