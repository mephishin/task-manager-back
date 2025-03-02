package com.example.taskmanagerback.adapter.controller;

import com.example.taskmanagerback.app.api.GetTasksByProject;
import com.example.taskmanagerback.model.task.Task;
import com.example.taskmanagerback.model.task.TaskStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.taskmanagerback.model.task.TaskStatus.*;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    GetTasksByProject getTasksByProject;

    @GetMapping("/{projectName}")
    public List<Task> getAllTasks(@PathVariable String projectName) {
        log.info("Requested tasks by projectName: {}", projectName);
        return getTasksByProject.execute(projectName);
    }

    @GetMapping("/statuses")
    public List<TaskStatus> getAllTaskStatuses() {
        log.info("Requested all task statuses");
        return List.of(
                TO_DO,
                IN_PROGRESS,
                REVIEW,
                IN_REVIEW,
                DONE,
                CLOSED
                );
    }
}
