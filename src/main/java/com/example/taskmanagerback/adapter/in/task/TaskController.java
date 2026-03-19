package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.CreateTaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.SearchTaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.TaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.UpdateTaskDto;
import com.example.taskmanagerback.adapter.repository.postgres.task.TaskRepo;
import com.example.taskmanagerback.app.api.security.GetAuthUser;
import com.example.taskmanagerback.app.api.task.CloseTask;
import com.example.taskmanagerback.app.api.task.CreateTask;
import com.example.taskmanagerback.app.api.task.GetTaskByKey;
import com.example.taskmanagerback.app.api.task.UpdateTask;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @GetMapping("/searchTasks")
    public List<SearchTaskDto> getSearchTasks() {
        log.info("Requested all task to search");
        return taskMapper.toListOfSearchTaskDto(taskRepo.findAll());
    }

    @PutMapping("/task")
    public TaskDto updateTask(
            @RequestBody UpdateTaskDto updateTaskDto
    ) {
        log.info("Request to update task with body: {}", updateTaskDto);
        return taskMapper.taskToTaskDto(
                updateTask.execute(
                        taskMapper.updateTaskDtoToTask(updateTaskDto)
                )
        );
    }

    @GetMapping("/task/{key}")
    public TaskDto getTask(
            @PathVariable String key
    ) {
        log.info("Requested task by key: {}", key);
        return taskMapper.taskToTaskDto(getTaskByKey.execute(key));
    }

    @PostMapping("/task")
    public TaskDto createTask(
            @RequestBody CreateTaskDto createTaskDto,
            JwtAuthenticationToken jwtAuthenticationToken
    ) {
        log.info("Request to create task: {}", createTaskDto);
        return taskMapper.taskToTaskDto(
                createTask.execute(
                        taskMapper.createTaskDtoToTask(createTaskDto),
                        getAuthUser.execute(jwtAuthenticationToken)
                )
        );
    }

    @DeleteMapping("/task/{key}")
    public void closeTask(
            @PathVariable String key
    ) {
        log.info("Request to close task with key: {}", key);
        closeTask.execute(key);
    }
}
