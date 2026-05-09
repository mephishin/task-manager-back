package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.CreateTaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.TaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.UpdateTaskDto;
import com.example.taskmanagerback.adapter.out.repository.postgres.task.TaskRepo;
import com.example.taskmanagerback.app.api.in.security.GetAuthUser;
import com.example.taskmanagerback.app.api.in.task.*;
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

    @GetMapping()
    public List<TaskDto> getTasks() {
        log.info("Requested all task");
        return taskMapper.listOfTasksToListOfTasksDto(taskRepo.findAll()).stream()
                .sorted(Comparator.comparing(TaskDto::key))
                .toList();
    }

    @GetMapping(params = "projectId")
    public List<TaskDto> getTasksByProject(@RequestParam String projectId) {
        log.info("Requested all task to search by project");
        return taskMapper.listOfTasksToListOfTasksDto(getTasksByProject.execute(projectId))
                .stream()
                .sorted(Comparator.comparing(TaskDto::key))
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
    public String createTask(
            @RequestBody CreateTaskDto createTaskDto,
            JwtAuthenticationToken jwtAuthenticationToken
    ) {
        log.info("Request to create task: {}", createTaskDto);
        return createTask.execute(
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
