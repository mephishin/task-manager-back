package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.TasksPageDto;
import com.example.taskmanagerback.adapter.in.task.dto.TaskDto;
import com.example.taskmanagerback.adapter.repository.project.ProjectRepo;
import com.example.taskmanagerback.adapter.repository.task.ParticipantRepo;
import com.example.taskmanagerback.adapter.repository.task.TaskStatusRepo;
import com.example.taskmanagerback.adapter.repository.task.TaskTypeRepo;
import com.example.taskmanagerback.model.project.Project;
import com.example.taskmanagerback.model.task.Task;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskMapper {
    ProjectRepo projectRepo;
    TaskStatusRepo taskStatusRepo;
    TaskTypeRepo taskTypeRepo;
    ParticipantRepo participantRepo;

    public TasksPageDto listOfTasksToListOfTasksDto(List<Task> tasks, Project project) {
        var getAllTasks = new TasksPageDto();

        getAllTasks.setParticipants(tasks.stream()
                .map(Task::getAssignee)
                .distinct()
                .map(participant -> TasksPageDto.Participant.builder()
                        .username(participant.getUsername())
                        .tasks(tasks.stream()
                                .filter(task -> task.getAssignee().getUsername().equals(participant.getUsername()))
                                .map(task -> TasksPageDto.Participant.Task.builder()
                                        .key(task.getKey())
                                        .name(task.getName())
                                        .type(task.getType().getValue())
                                        .status(task.getStatus().getValue())
                                        .build()

                                )
                                .toList()
                        )
                        .build()
                )
                .toList()
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
                .assignee(task.getAssignee().getUsername())
                .reporter(task.getReporter().getUsername())
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
                )
                .setAssignee(
                        participantRepo.findByUsername(
                                taskDto.getAssignee()
                        ).orElseThrow()
                )
                .setReporter(
                        participantRepo.findByUsername(
                                taskDto.getReporter()
                        ).orElseThrow()
                );
    }
}
