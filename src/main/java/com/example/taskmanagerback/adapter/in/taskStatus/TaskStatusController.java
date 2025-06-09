package com.example.taskmanagerback.adapter.in.taskStatus;

import com.example.taskmanagerback.app.api.task.*;
import com.example.taskmanagerback.model.task.constants.TaskStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", methods = {POST, GET, PUT, DELETE})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskStatusController {
    UpdateTaskStatus updateTaskStatus;
    GetAllowedTaskStatuses getAllowedTaskStatuses;
    TaskStatusMapper taskStatusMapper;


    @GetMapping("/statuses")
    public List<String> getTaskStatuses() {
        log.info("Requested all task statuses");
        return taskStatusMapper.arrayOfTaskStatusToListOfTaskStatusValues(Arrays.asList(TaskStatus.values()));
    }

    @PutMapping("/task/{key}/status/{status}")
    public void updateTaskStatus(
            @PathVariable String key,
            @PathVariable String status)
    {
        log.info("Request to push task status of task with key: {}", key);
        updateTaskStatus.execute(key, taskStatusMapper.taskStatusValueToTaskStatus(status));
    }

    @GetMapping(path = "/task/{key}/statuses", params = "allowed=true")
    public List<String> getAllowedTaskStatuses(
            @PathVariable String key
    ) {
        log.info("Requested allowed task statuses of task with key: {}", key);
        return taskStatusMapper.arrayOfTaskStatusToListOfTaskStatusValues(getAllowedTaskStatuses.execute(key));
    }
}