package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.*;
import com.example.taskmanagerback.adapter.repository.task.ParticipantRepo;
import com.example.taskmanagerback.app.api.project.GetProjectByName;
import com.example.taskmanagerback.model.task.Task;
import com.example.taskmanagerback.model.task.TimeInterval;
import com.example.taskmanagerback.util.DateTimeUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Mapper(componentModel = "spring")
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class TaskMapper {
    @Autowired
    GetProjectByName getProjectByName;
    @Autowired
    ParticipantRepo participantRepo;

    @Mapping(target = "project", source = "task.project.name")
    @Mapping(target = "status", expression = "java(task.getStatus().name())")
    @Mapping(target = "type", expression = "java(task.getType().name())")
    @Mapping(target = "assignee", source = "task.assignee.username")
    @Mapping(target = "reporter", source = "task.reporter.username")
    @Mapping(target = "created", qualifiedByName = "getFormattedInstant", source = "task.taskTime.created")
    @Mapping(target = "edited", qualifiedByName = "getFormattedInstant", source = "task.taskTime.edited")
    @Mapping(target = "total", qualifiedByName = "getTotal", source = "task.taskTime.timeIntervals")
    public abstract TaskDto taskToTaskDto(Task task);

    @Named("getFormattedInstant")
    protected String getFormattedInstant(Instant instant) {
        return Optional.ofNullable(instant)
                .map(TaskMapper::formatInstant)
                .orElse(null);
    }

    private static String formatInstant(Instant value) {
        return DateTimeUtils.DATE_TIME_FORMAT.format(value.atZone(ZoneId.of("Europe/Moscow")));
    }

    @Named("getTotal")
    protected String getTotal(List<TimeInterval> timeIntervals) {
        return Optional.ofNullable(timeIntervals)
                .flatMap(intervals -> intervals.stream()
                    .filter(timeInterval -> nonNull(timeInterval.getStarted()))
                    .filter(timeInterval -> nonNull(timeInterval.getStopped()))
                    .map(timeInterval -> Duration.between(timeInterval.getStarted(), timeInterval.getStopped()))
                    .reduce(Duration::plus)
                    .map(duration -> duration.toDaysPart() + " days " + duration.toHoursPart() + " hours " + duration.toMinutesPart() + " minutes")
                ).orElse(null);
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
