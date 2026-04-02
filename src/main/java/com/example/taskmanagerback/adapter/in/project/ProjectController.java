package com.example.taskmanagerback.adapter.in.project;

import com.example.taskmanagerback.adapter.in.project.dto.CreateProjectDto;
import com.example.taskmanagerback.adapter.in.project.dto.ProjectDto;
import com.example.taskmanagerback.adapter.repository.minio.File;
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
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    SaveProjectFile saveProjectFile;
    DeleteProjectFile deleteProjectFile;

    @GetMapping
    public List<ProjectDto> getAllProjects() {
        log.info("Requested all projects");
        return getAllProjects.execute().stream()
                .map(projectMapper::projectToProjectDto)
                .toList();
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

    @PostMapping("/{projectId}/file")
    public void saveProjectFile(
            @PathVariable("projectId") String projectId,
            @RequestParam("zippedFiles") MultipartFile file
    ) throws IOException {
        log.info("Request to save project file {} {}", file.getOriginalFilename(), file.getContentType());
        saveProjectFile.execute(
                projectId,
                ZipUtils.unzip(file)
        );
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

        return projectMapper.projectToProjectDto(
                getAuthUser.execute(getJwtAuthenticationToken.execute()).getProject()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('task-manager_leader', 'task-manager_admin')")
    public ProjectDto createProject(@RequestBody CreateProjectDto createProjectDto) {
        log.info("Requested creating project by auth participant");

        return projectMapper.projectToProjectDto(
                createProject.execute(projectMapper.projectDtoToProject(createProjectDto)));
    }
}
