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
        return new TasksChartDto(
                null,
                getUsers(tasks),
                getNotAssignedTasks(tasks)
        );
    }

    private List<TasksChartDto.Participant.Task> getNotAssignedTasks(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> isNull(task.getAssignee()))
                .map(task -> new TasksChartDto.Participant.Task(
                        task.getKey(),
                        task.getName(),
                        task.getStatus().getValue(),
                        task.getType().getValue()
                ))
                .toList();
    }

    private List<TasksChartDto.Participant> getUsers(List<Task> tasks) {
        return tasks.stream()
                .map(Task::getAssignee)
                .filter(Objects::nonNull)
                .distinct()
                .map(user -> new TasksChartDto.Participant(
                        user.getUsername(),
                        user.getFirstName(),
                        user.getMiddleName(),
                        user.getLastName(),
                        user.getGroup(),
                        tasks.stream()
                                .filter(task -> nonNull(task.getAssignee()) &&
                                        task.getAssignee().getUsername().equals(user.getUsername()))
                                .map(task -> new TasksChartDto.Participant.Task(
                                        task.getKey(),
                                        task.getName(),
                                        task.getType().getValue(),
                                        task.getStatus().getValue()
                                ))
                                .toList()
                ))
                .toList();
    }
}
