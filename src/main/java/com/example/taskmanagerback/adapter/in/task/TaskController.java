package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.CreateTaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.SearchTaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.TaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.UpdateTaskDto;
import com.example.taskmanagerback.adapter.repository.postgres.task.TaskRepo;
import com.example.taskmanagerback.app.api.security.GetAuthUser;
import com.example.taskmanagerback.app.api.task.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("isAuthenticated()")
public class TaskController {
    TaskMapper taskMapper;
    UpdateTask updateTask;
    CreateTask createTask;
    GetTaskByKey getTaskByKey;
    GetAuthUser getAuthUser;
    CloseTask closeTask;
    TaskRepo taskRepo;
    GetTasksByProject getTasksByProject;

    @GetMapping(value = "/search")
    public List<SearchTaskDto> getSearchTasks() {
        log.info("Requested all task to search");
        return taskMapper.toListOfSearchTaskDto(taskRepo.findAll()).stream()
                .sorted(Comparator.comparing(SearchTaskDto::key))
                .toList();
    }

    @GetMapping(value = "/search", params = "filter=userProject")
    public List<SearchTaskDto> getSearchTasksByUserProject(JwtAuthenticationToken jwtAuthenticationToken) {
        log.info("Requested all task to search by user project");
        return taskMapper.toListOfSearchTaskDto(getTasksByProject.execute(
                        getAuthUser.execute(jwtAuthenticationToken).getProject().getKey()))
                .stream()
                .sorted(Comparator.comparing(SearchTaskDto::key))
                .toList();
    }

    @PutMapping
    public void updateTask(
            @RequestBody UpdateTaskDto updateTaskDto
    ) {
        log.info("Request to update task with body: {}", updateTaskDto);
        updateTask.execute(
                taskMapper.updateTaskDtoToTask(updateTaskDto)
        );

    }

    @GetMapping("/{key}")
    public TaskDto getTask(
            @PathVariable String key
    ) {
        log.info("Requested task by key: {}", key);
        return taskMapper.taskToTaskDto(getTaskByKey.execute(key));
    }

    @PostMapping
    public void createTask(
            @RequestBody CreateTaskDto createTaskDto,
            JwtAuthenticationToken jwtAuthenticationToken
    ) {
        log.info("Request to create task: {}", createTaskDto);
        createTask.execute(
                taskMapper.createTaskDtoToTask(createTaskDto),
                getAuthUser.execute(jwtAuthenticationToken)
        );

    }

    @DeleteMapping("/{key}")
    public void closeTask(
            @PathVariable String key
    ) {
        log.info("Request to close task with key: {}", key);
        closeTask.execute(key);
    }
}
