package com.example.taskmanagerback.adapter.in.tasksChart;

import com.example.taskmanagerback.adapter.in.tasksChart.dto.TasksChartDto;
import com.example.taskmanagerback.app.api.in.security.GetAuthUser;
import com.example.taskmanagerback.app.api.in.security.GetJwtAuthenticationToken;
import com.example.taskmanagerback.app.api.in.task.GetTasksByProject;
import com.example.taskmanagerback.model.task.Task;
import com.example.taskmanagerback.model.task.constants.TaskStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("isAuthenticated()")
public class TasksChartController {
    GetJwtAuthenticationToken getJwtAuthenticationToken;
    GetTasksByProject getTasksByProject;
    TasksChartMapper tasksChartMapper;
    GetAuthUser getAuthUser;

    @GetMapping(path = "/tasksChart", params = "projectId")
    public TasksChartDto getTasksChart(
            @RequestParam String projectId
    ) {
        log.info("Requested tasks by projectName: {}", projectId);
        return tasksChartMapper.listOfTasksToTasksChartDto(
                filterTasksExceptClosed(getTasksByProject.execute(projectId))
        );
    }

    @GetMapping(path = "/tasksChart", params = "!projectId")
    public TasksChartDto getTasksChart() {
        var authUsersProject =
                getAuthUser.execute(getJwtAuthenticationToken.execute()).getProject();
        log.info("Requested tasks by auth user's project: {}", authUsersProject.getName());
        return tasksChartMapper.listOfTasksToTasksChartDto(
                filterTasksExceptClosed(getTasksByProject.execute(authUsersProject.getKey()))
        );
    }

    private List<Task> filterTasksExceptClosed(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> !task.getStatus().equals(TaskStatus.CLOSED))
                .toList();
    }
}