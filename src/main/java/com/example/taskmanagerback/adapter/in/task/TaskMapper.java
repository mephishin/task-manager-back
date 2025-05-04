package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.CreateTaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.TasksPageDto;
import com.example.taskmanagerback.adapter.in.task.dto.TaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.UpdateTaskDto;
import com.example.taskmanagerback.adapter.repository.task.ParticipantRepo;
import com.example.taskmanagerback.adapter.repository.task.TaskStatusRepo;
import com.example.taskmanagerback.adapter.repository.task.TaskTypeRepo;
import com.example.taskmanagerback.app.api.project.GetProjectByName;
import com.example.taskmanagerback.model.task.Task;
import com.example.taskmanagerback.util.DateTimeUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Mapper(componentModel = "spring")
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class TaskMapper {
    @Autowired
    GetProjectByName getProjectByName;
    @Autowired
    TaskStatusRepo taskStatusRepo;
    @Autowired
    TaskTypeRepo taskTypeRepo;
    @Autowired
    ParticipantRepo participantRepo;

    public TasksPageDto listOfTasksToListOfTasksDto(List<Task> tasks) {

        return TasksPageDto.builder()
                .participants(tasks.stream()
                        .map(Task::getAssignee)
                        .filter(Objects::nonNull)
                        .distinct()
                        .map(participant -> TasksPageDto.Participant.builder()
                                .username(participant.getUsername())
                                .tasks(tasks.stream()
                                        .filter(task -> nonNull(task.getAssignee()) &&
                                                task.getAssignee().getUsername().equals(participant.getUsername()))
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
                )
                .notAssignedTasks(tasks.stream()
                        .filter(task -> isNull(task.getAssignee()))
                        .map(task -> TasksPageDto.Participant.Task.builder()
                                .key(task.getKey())
                                .name(task.getName())
                                .status(task.getStatus().getValue())
                                .type(task.getType().getValue())
                                .build()
                        )
                        .toList()
                )
                .build();
    }

    public TaskDto taskToTaskDto(Task task) {
        return TaskDto.builder()
                .key(task.getKey())
                .name(task.getName())
                .description(task.getDescription())
                .project(task.getProject().getName())
                .status(task.getStatus().getValue())
                .type(task.getType().getValue())
                .assignee(isNull(task.getAssignee()) ? null : task.getAssignee().getUsername())
                .reporter(task.getReporter().getUsername())
                .created(Optional.ofNullable(task.getCreated())
                        .map(edited -> edited.atZone(ZoneId.of("Europe/Moscow")))
                        .map(DateTimeUtils.DATE_TIME_FORMAT::format)
                        .orElse(null))
                .edited(Optional.ofNullable(task.getEdited())
                        .map(edited -> edited.atZone(ZoneId.of("Europe/Moscow")))
                        .map(DateTimeUtils.DATE_TIME_FORMAT::format)
                        .orElse(null))
                .build();
    }

    public Task createTaskDtoToTask(CreateTaskDto createTaskDto) {
        return new Task()
                .setName(createTaskDto.getName())
                .setDescription(createTaskDto.getDescription())
                .setType(taskTypeRepo.findByValue(createTaskDto.getType()).orElseThrow())
                .setProject(getProjectByName.execute(createTaskDto.getProject()))
                .setAssignee(isNull(createTaskDto.getAssignee()) ? null : participantRepo.findByUsername(createTaskDto.getAssignee()).orElseThrow());
    }

    public Task updateTaskDtoToTask(UpdateTaskDto updateTaskDto) {
        return new Task()
                .setKey(updateTaskDto.getKey())
                .setName(updateTaskDto.getName())
                .setDescription(updateTaskDto.getDescription())
                .setStatus(taskStatusRepo.findByValue(updateTaskDto.getStatus()).orElseThrow())
                .setType(taskTypeRepo.findByValue(updateTaskDto.getType()).orElseThrow())
                .setAssignee(isNull(updateTaskDto.getAssignee()) ? null : participantRepo.findByUsername(updateTaskDto.getAssignee()).orElseThrow());
    }
}
