package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.ListOfTasksDto;
import com.example.taskmanagerback.adapter.in.task.dto.TaskDto;
import com.example.taskmanagerback.adapter.repository.project.ProjectRepo;
import com.example.taskmanagerback.adapter.repository.task.TaskStatusRepo;
import com.example.taskmanagerback.adapter.repository.task.TaskTypeRepo;
import com.example.taskmanagerback.model.task.Task;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskMapper {
    ProjectRepo projectRepo;
    TaskStatusRepo taskStatusRepo;
    TaskTypeRepo taskTypeRepo;

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

    @Transactional
    public Task taskDtoToTask(TaskDto taskDto) {
        return new Task()
                .setKey(taskDto.getKey())
                .setName(taskDto.getName())
                .setDescription(taskDto.getDescription())
                .setStatus(
                        taskStatusRepo.findByValue(
                                taskDto.getStatus()
                        ).orElseThrow()
                )
                .setType(
                        taskTypeRepo.findByValue(
                                taskDto.getType()
                        ).orElseThrow()
                )
                .setProject(
                        projectRepo.findByName(
                                taskDto.getProject()
                        ).orElseThrow()
                );
    }
}
