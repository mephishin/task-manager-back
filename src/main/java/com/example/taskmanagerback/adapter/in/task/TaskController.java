package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.ListOfTasksDto;
import com.example.taskmanagerback.adapter.in.task.dto.TaskDto;
import com.example.taskmanagerback.adapter.repository.task.TaskStatusRepo;
import com.example.taskmanagerback.adapter.repository.task.TaskTypeRepo;
import com.example.taskmanagerback.app.api.task.GetTaskByKey;
import com.example.taskmanagerback.app.api.task.GetTasksByProject;
import com.example.taskmanagerback.app.api.task.UpdateTask;
import com.example.taskmanagerback.model.task.TaskStatus;
import com.example.taskmanagerback.model.task.TaskType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", methods = {POST, GET})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    TaskMapper taskMapper;
    GetTasksByProject getTasksByProject;
    UpdateTask updateTask;
    GetTaskByKey getTaskByKey;
    TaskTypeRepo taskTypeRepo;
    TaskStatusRepo taskStatusRepo;

    @GetMapping("/all/{projectName}")
    public ListOfTasksDto getAllTasks(@PathVariable String projectName) {
        log.info("Requested tasks by projectName: {}", projectName);
        return taskMapper.listOfTasksToListOfTasksDto(getTasksByProject.execute(projectName));
    }

    @GetMapping("/statuses")
    public List<TaskStatus> getAllTaskStatuses() {
        log.info("Requested all task statuses");
        return taskStatusRepo.findAll();
    }

    @PostMapping("/update")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        log.info("Request to update task with body: {}", taskDto);
        return taskMapper.taskToTaskDto(updateTask.execute(taskDto));
    }

    @GetMapping("/{key}")
    public TaskDto getTaskByKey(@PathVariable String key) {
        return taskMapper.taskToTaskDto(getTaskByKey.execute(key));
    }

    @GetMapping("/types")
    public List<TaskType> getAllTaskTypes() {
        log.info("Requested all task statuses");
        return taskTypeRepo.findAll();
    }
}
