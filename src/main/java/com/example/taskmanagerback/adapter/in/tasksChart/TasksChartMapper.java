package com.example.taskmanagerback.adapter.in.tasksChart;

import com.example.taskmanagerback.adapter.in.tasksChart.dto.TasksChartDto;
import com.example.taskmanagerback.model.task.Task;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Mapper(componentModel = "spring")
public abstract class TasksChartMapper {
    public TasksChartDto listOfTasksToTasksChartDto(List<Task> tasks) {
        return TasksChartDto.builder()
                .participants(getParticipants(tasks))
                .notAssignedTasks(getNotAssignedTasks(tasks))
                .build();
    }

    private List<TasksChartDto.Participant.Task> getNotAssignedTasks(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> isNull(task.getAssignee()))
                .map(task -> TasksChartDto.Participant.Task.builder()
                        .key(task.getKey())
                        .name(task.getName())
                        .status(task.getStatus().getValue())
                        .type(task.getType().getValue())
                        .build())
                .toList();
    }

    private List<TasksChartDto.Participant> getParticipants(List<Task> tasks) {
        return tasks.stream()
                .map(Task::getAssignee)
                .filter(Objects::nonNull)
                .distinct()
                .map(participant -> TasksChartDto.Participant.builder()
                        .username(participant.getUsername())
                        .tasks(tasks.stream()
                                .filter(task -> nonNull(task.getAssignee()) &&
                                        task.getAssignee().getUsername().equals(participant.getUsername()))
                                .map(task -> TasksChartDto.Participant.Task.builder()
                                        .key(task.getKey())
                                        .name(task.getName())
                                        .type(task.getType().getValue())
                                        .status(task.getStatus().getValue())
                                        .build())
                                .toList())
                        .build())
                .toList();
    }
}
