package com.example.taskmanagerback.adapter.in.tasksChart;

import com.example.taskmanagerback.adapter.in.tasksChart.dto.TasksChartDto;
import com.example.taskmanagerback.app.api.security.GetAuthParticipant;
import com.example.taskmanagerback.app.api.security.GetJwtAuthenticationToken;
import com.example.taskmanagerback.app.api.task.GetTasksByProject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", methods = {POST, GET, PUT, DELETE})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TasksChartController {
    GetJwtAuthenticationToken getJwtAuthenticationToken;
    GetTasksByProject getTasksByProject;
    TasksChartMapper tasksChartMapper;
    GetAuthParticipant getAuthParticipant;

    @GetMapping(path = "/tasksChart", params = "project")
    public TasksChartDto getTasksChart(
            @RequestParam String project
    ) {
        log.info("Requested tasks by projectName: {}", project);
        return tasksChartMapper.listOfTasksToTasksChartDto(
                getTasksByProject.execute(project)
        );
    }

    @GetMapping(path = "/tasksChart", params = "!project")
    public TasksChartDto getTasksChart() {
        var authParticipantsProject =
                getAuthParticipant.execute(getJwtAuthenticationToken.execute()).getProject();
        log.info("Requested tasks by auth participant's project: {}", authParticipantsProject.getName());
        return tasksChartMapper.listOfTasksToTasksChartDto(
                getTasksByProject.execute(authParticipantsProject.getName())
        );
    }
}