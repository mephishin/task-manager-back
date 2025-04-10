package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.CreateTaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.TasksPageDto;
import com.example.taskmanagerback.adapter.in.task.dto.TaskDto;
import com.example.taskmanagerback.adapter.repository.task.TaskStatusRepo;
import com.example.taskmanagerback.adapter.repository.task.TaskTypeRepo;
import com.example.taskmanagerback.app.api.project.GetProjectByName;
import com.example.taskmanagerback.app.api.security.GetAuthParticipant;
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

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/task")
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
    GetProjectByName getProjectByName;

    @GetMapping("/all/{projectName}")
    public TasksPageDto getAllTasks(@PathVariable String projectName) {
        log.info("Requested tasks by projectName: {}", projectName);
        return taskMapper.listOfTasksToListOfTasksDto(getTasksByProject.execute(projectName));
    }

    @GetMapping("/statuses")
    public List<TaskStatus> getAllTaskStatuses() {
        log.info("Requested all task statuses");
        return taskStatusRepo.findAll();
    }

    @PutMapping
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        log.info("Request to update task with body: {}", taskDto);
        return taskMapper.taskToTaskDto(
                updateTask.execute(
                        taskMapper.taskDtoToTask(taskDto)
                )
        );
    }

    @GetMapping("/{key}")
    public TaskDto getTaskByKey(@PathVariable String key) {
        log.info("Requested task by key: {}", key);
        return taskMapper.taskToTaskDto(getTaskByKey.execute(key));
    }

    @GetMapping("/types")
    public List<TaskType> getAllTaskTypes() {
        log.info("Requested all task types");
        return taskTypeRepo.findAll();
    }

    @PostMapping
    public TaskDto createTask(@RequestBody CreateTaskDto createTaskDto, JwtAuthenticationToken jwtAuthenticationToken) {
        log.info("Request to create task: {}", createTaskDto);
        return taskMapper.taskToTaskDto(
                createTask.execute(
                        taskMapper.createTaskDtoToTask(createTaskDto),
                        getAuthParticipant.execute(jwtAuthenticationToken)
                )
        );
    }

    @GetMapping("/all")
    public TasksPageDto getAllTaskByAuthParticipantProject(JwtAuthenticationToken authenticationToken) {
        var authParticipantProject = getAuthParticipant.execute(authenticationToken).getProject();
        log.info("Requested tasks by auth participant project: {}", authParticipantProject.getName());
        return taskMapper.listOfTasksToListOfTasksDto(getTasksByProject.execute(authParticipantProject.getName()));
    }
}
