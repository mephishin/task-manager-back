package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.*;
import com.example.taskmanagerback.app.api.security.GetAuthParticipant;
import com.example.taskmanagerback.app.api.security.GetJwtAuthenticationToken;
import com.example.taskmanagerback.app.api.task.*;
import com.example.taskmanagerback.model.task.constants.TaskStatus;
import com.example.taskmanagerback.model.task.constants.TaskType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", methods = {POST, GET, PUT, DELETE})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    TaskMapper taskMapper;
    GetTasksByProject getTasksByProject;
    UpdateTask updateTask;
    CreateTask createTask;
    GetTaskByKey getTaskByKey;
    GetAuthParticipant getAuthParticipant;
    GetJwtAuthenticationToken getJwtAuthenticationToken;
    UpdateTaskStatus updateTaskStatus;
    CloseTask closeTask;
    GetAllowedTaskStatuses getAllowedTaskStatuses;

    @GetMapping("/tasks")
    public TasksPageDto getTasks(
            @RequestParam Optional<String> project
    ) {
        if (project.isPresent()) {
            log.info("Requested tasks by projectName: {}", project.get());
            return taskMapper.listOfTasksToListOfTasksDto(getTasksByProject.execute(project.get()));
        } else {
            var authParticipantsProject =
                    getAuthParticipant.execute(getJwtAuthenticationToken.execute()).getProject();
            log.info("Requested tasks by auth participant's project: {}", authParticipantsProject.getName());
            return taskMapper.listOfTasksToListOfTasksDto(getTasksByProject.execute(authParticipantsProject.getName()));
        }
    }

    @GetMapping("/task/statuses")
    public List<String> getTaskStatuses() {
        log.info("Requested all task statuses");
        return Arrays.stream(TaskStatus.values())
                .map(Enum::name)
                .toList();
    }

    @GetMapping("/task/types")
    public List<String> getTaskTypes() {
        log.info("Requested all task types");
        return Arrays.stream(TaskType.values())
                .map(Enum::name)
                .toList();
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
                        getAuthParticipant.execute(jwtAuthenticationToken)
                )
        );
    }

    @PutMapping("/task/{key}/status")
    public void updateTaskStatus(
            @PathVariable String key,
            @RequestParam TaskStatus status
    ) {
        log.info("Request to push task status of task with key: {}", key);
        updateTaskStatus.execute(key, status);
    }

    @GetMapping("/task/{key}/allowedStatuses")
    public List<String> getAllowedTaskStatus(
            @PathVariable String key
    ) {
        log.info("Requested allowed task statuses of task with key: {}", key);
        return getAllowedTaskStatuses.execute(key);
    }

    @DeleteMapping("/task/{key}")
    public void closeTask(
            @PathVariable String key
    ) {
        log.info("Request to close task with key: {}", key);
        closeTask.execute(key);
    }
}
