package com.example.taskmanagerback.adapter.in.taskStatus;

import com.example.taskmanagerback.app.api.task.GetAllowedTaskStatuses;
import com.example.taskmanagerback.app.api.task.UpdateTaskStatus;
import com.example.taskmanagerback.app.api.task.status.GetProjectStatuses;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("isAuthenticated()")
public class TaskStatusController {
    UpdateTaskStatus updateTaskStatus;
    GetAllowedTaskStatuses getAllowedTaskStatuses;
    TaskStatusMapper taskStatusMapper;
    GetProjectStatuses getProjectStatuses;

    @GetMapping("/statuses/{projectId}")
    public List<String> getTaskStatuses(@PathVariable("projectId") String projectId) {
        log.info("Requested all task statuses");
        return taskStatusMapper.arrayOfTaskStatusToListOfTaskStatusValues(getProjectStatuses.execute(projectId));
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