package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.*;
import com.example.taskmanagerback.adapter.repository.task.ParticipantRepo;
import com.example.taskmanagerback.app.api.project.GetProjectByName;
import com.example.taskmanagerback.model.task.Task;
import com.example.taskmanagerback.model.task.TaskTime;

import com.example.taskmanagerback.util.DateTimeUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.ZoneId;
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
                                                .type(task.getType().name())
                                                .status(task.getStatus().name())
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
                                .status(task.getStatus().name())
                                .type(task.getType().name())
                                .build()
                        )
                        .toList()
                )
                .build();
    }

    public TaskDto taskToTaskDto(Task task) {
        return new TaskDto()
                .setKey(task.getKey())
                .setName(task.getName())
                .setDescription(task.getDescription())
                .setProject(task.getProject().getName())
                .setStatus(task.getStatus().name())
                .setType(task.getType().name())
                .setAssignee(isNull(task.getAssignee()) ? null : task.getAssignee().getUsername())
                .setReporter(task.getReporter().getUsername())
                .setCreated(Optional.ofNullable(task)
                        .map(Task::getTaskTime)
                        .map(TaskTime::getCreated)
                        .map(created -> created.atZone(ZoneId.of("Europe/Moscow")))
                        .map(DateTimeUtils.DATE_TIME_FORMAT::format)
                        .orElse(null))
                .setEdited(Optional.ofNullable(task)
                        .map(Task::getTaskTime)
                        .map(TaskTime::getEdited)
                        .map(edited -> edited.atZone(ZoneId.of("Europe/Moscow")))
                        .map(DateTimeUtils.DATE_TIME_FORMAT::format)
                        .orElse(null))
                .setTotal(Optional.ofNullable(task)
                        .map(Task::getTaskTime)
                        .map(TaskTime::getTimeIntervals)
                        .orElse(List.of())
                        .stream()
                        .filter(timeInterval -> nonNull(timeInterval.getStarted()))
                        .filter(timeInterval -> nonNull(timeInterval.getStopped()))
                        .map(timeInterval -> Duration.between(timeInterval.getStarted(), timeInterval.getStopped()))
                        .reduce(Duration::plus)
                        .map(duration -> duration.toDaysPart() + " days " + duration.toHoursPart() + " hours " + duration.toMinutesPart() + " minutes")
                        .orElse(null));
    }

    @Mapping(target = "type", expression = "java(TaskType.valueOf(createTaskDto.getType()))")
    @Mapping(target = "project", expression = "java(getProjectByName.execute(createTaskDto.getProject()))")
    @Mapping(target = "assignee", expression = "java(participantRepo.findByUsername(createTaskDto.getAssignee()).orElseThrow())")
    public abstract Task createTaskDtoToTask(CreateTaskDto createTaskDto);

    @Mapping(target = "assignee", expression = "java(participantRepo.findByUsername(updateTaskDto.getAssignee()).orElseThrow())")
    public abstract Task updateTaskDtoToTask(UpdateTaskDto updateTaskDto);

    public abstract List<SearchTaskDto> toListOfSearchTaskDto(List<Task> tasks);

    @Mapping(target = "project", source = "task.project.name")
    @Mapping(target = "assignee", source = "task.assignee.username")
    @Mapping(target = "reporter", source = "task.reporter.username")
    protected abstract SearchTaskDto taskToSearchTaskDto(Task task);
}
