package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.CreateTaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.TasksPageDto;
import com.example.taskmanagerback.adapter.in.task.dto.TaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.UpdateTaskDto;
import com.example.taskmanagerback.adapter.repository.task.TaskStatusRepo;
import com.example.taskmanagerback.adapter.repository.task.TaskTypeRepo;
import com.example.taskmanagerback.app.api.security.GetAuthParticipant;
import com.example.taskmanagerback.app.api.security.GetJwtAuthenticationToken;
import com.example.taskmanagerback.app.api.task.CreateTask;
import com.example.taskmanagerback.app.api.task.GetTaskByKey;
import com.example.taskmanagerback.app.api.task.GetTasksByProject;
import com.example.taskmanagerback.app.api.task.UpdateTask;
import com.example.taskmanagerback.model.task.TaskStatus;
import com.example.taskmanagerback.model.task.TaskType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", methods = {POST, GET, PUT})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    TaskMapper taskMapper;
    GetTasksByProject getTasksByProject;
    UpdateTask updateTask;
    CreateTask createTask;
    GetTaskByKey getTaskByKey;
    TaskTypeRepo taskTypeRepo;
    TaskStatusRepo taskStatusRepo;
    GetAuthParticipant getAuthParticipant;
    GetJwtAuthenticationToken getJwtAuthenticationToken;

    @GetMapping("/tasks")
    public TasksPageDto getTasks(@RequestParam Optional<String> project) {
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
    public List<TaskStatus> getTaskStatuses() {
        log.info("Requested all task statuses");
        return taskStatusRepo.findAll();
    }

    @PutMapping("/task")
    public TaskDto updateTask(@RequestBody UpdateTaskDto updateTaskDto) {
        log.info("Request to update task with body: {}", updateTaskDto);
        return taskMapper.taskToTaskDto(
                updateTask.execute(
                        taskMapper.updateTaskDtoToTask(updateTaskDto)
                )
        );
    }

    @GetMapping("/task/{key}")
    public TaskDto getTask(@PathVariable String key) {
        log.info("Requested task by key: {}", key);
        return taskMapper.taskToTaskDto(getTaskByKey.execute(key));
    }

    @GetMapping("/task/types")
    public List<TaskType> getTaskTypes() {
        log.info("Requested all task types");
        return taskTypeRepo.findAll();
    }

    @PostMapping("/task")
    public TaskDto createTask(@RequestBody CreateTaskDto createTaskDto, JwtAuthenticationToken jwtAuthenticationToken) {
        log.info("Request to create task: {}", createTaskDto);
        return taskMapper.taskToTaskDto(
                createTask.execute(
                        taskMapper.createTaskDtoToTask(createTaskDto),
                        getAuthParticipant.execute(jwtAuthenticationToken)
                )
        );
    }
}
