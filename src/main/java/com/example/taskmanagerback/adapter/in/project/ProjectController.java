package com.example.taskmanagerback.adapter.in.project;

import com.example.taskmanagerback.adapter.in.project.dto.CreateProjectDto;
import com.example.taskmanagerback.adapter.in.project.dto.ProjectDto;
import com.example.taskmanagerback.adapter.repository.minio.File;
import com.example.taskmanagerback.adapter.repository.postgres.project.ProjectRepo;
import com.example.taskmanagerback.app.api.project.*;
import com.example.taskmanagerback.app.api.security.GetAuthUser;
import com.example.taskmanagerback.app.api.security.GetJwtAuthenticationToken;
import com.example.taskmanagerback.util.ZipUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/project")
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
    UpdateProject updateProject;
    DeleteProjectFile deleteProjectFile;
    ProjectRepo projectRepo;
    AcceptProjectInvite acceptProjectInvite;
    GetProjectInvite getProjectInvite;

    @GetMapping
    public List<ProjectDto> getAllProjects() {
        log.info("Requested all projects");
        return getAllProjects.execute().stream()
                .map(projectMapper::projectToProjectDto)
                .toList();
    }

    @GetMapping("/{projectId}")
    public ProjectDto getProject(@PathVariable("projectId") String projectId) {
        log.info("Requested project by id: {}", projectId);
        return projectMapper.projectToProjectDto(projectRepo.findById(projectId).orElseThrow());
    }

    @GetMapping("/{projectId}/file")
    public void getAllProjectFiles(@PathVariable("projectId") String projectId, HttpServletResponse response) {
        log.info("Requested all projects files");
        var listOfFiles = getAllProjectsFiles.execute(projectId);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"files.zip\"");

        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            for (File file : listOfFiles) {
                ZipEntry zipEntry = new ZipEntry(file.name());
                zipEntry.setSize(file.bytes().length);

                zipOut.putNextEntry(zipEntry);
                StreamUtils.copy(new ByteArrayInputStream(file.bytes()), zipOut);

                zipOut.closeEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{projectId}")
    public void updateProjectInfo(
            @PathVariable("projectId") String projectId,
            @RequestParam(value = "zippedFiles", required = false) MultipartFile zippedFiles,
            @RequestParam(value = "description", required = false) String description
    ) throws IOException {
        log.info("Request to update project with id: {}", projectId);
        updateProject.execute(
                projectId,
                description,
                (nonNull(zippedFiles) && !zippedFiles.isEmpty()) ? ZipUtils.unzip(zippedFiles) : null
        );
    }

    @PutMapping("/acceptInvite/{inviteKey}")
    public String updateProjectParticipants(
            @PathVariable("inviteKey") String inviteKey,
            JwtAuthenticationToken jwtAuthenticationToken
    ) {
        log.info("Request to accept invite with key: {}", inviteKey);
        return acceptProjectInvite.execute(inviteKey, jwtAuthenticationToken);
    }

    @GetMapping("/{projectId}/invite")
    public String acceptProjectInvite(
            @PathVariable("projectId") String projectId
    ) {
        log.info("Request to get invite to project with id: {}", projectId);
        return getProjectInvite.execute(projectId);
    }

    @DeleteMapping("/{projectId}/file")
    public void saveProjectFile(
            @PathVariable("projectId") String projectId,
            @RequestParam("filename") String filename
    ) {
        log.info("Request to delete project file {}", filename);
        deleteProjectFile.execute(projectId, filename);
    }

    @GetMapping(params = "filter=auth")
    public ProjectDto getProjectByAuthUser(Principal principal) {
        log.info("Requested project by auth participant {}", principal.getName());

        return Optional.ofNullable( getAuthUser.execute(getJwtAuthenticationToken.execute()).getProject())
                .map(projectMapper::projectToProjectDto)
                .orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('task-manager_leader', 'task-manager_admin')")
    public void createProject(@RequestBody CreateProjectDto createProjectDto) {
        log.info("Requested creating project by auth participant");

        createProject.execute(projectMapper.projectDtoToProject(createProjectDto));
    }
}
