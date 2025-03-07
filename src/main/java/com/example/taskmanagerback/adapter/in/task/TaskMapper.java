package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.ListOfTasksDto;
import com.example.taskmanagerback.adapter.in.task.dto.TaskDto;
import com.example.taskmanagerback.model.task.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public ListOfTasksDto listOfTasksToListOfTasksDto(List<Task> tasks) {
        var getAllTasks = new ListOfTasksDto();

        getAllTasks.setTasks(
                tasks.stream().map(task ->
                    ListOfTasksDto.Task.builder()
                            .key(task.getKey())
                            .status(task.getStatus().getValue())
                            .type(task.getType().getValue())
                            .name(task.getName())
                            .build()
                ).collect(Collectors.toList())
        );

        return getAllTasks;
    }

    public TaskDto taskToTaskDto(Task task) {
        return TaskDto.builder()
                .key(task.getKey())
                .name(task.getName())
                .description(task.getDescription())
                .project(task.getProject().getName())
                .status(task.getStatus().getValue())
                .type(task.getType().getValue())
                .build();
    }
}
