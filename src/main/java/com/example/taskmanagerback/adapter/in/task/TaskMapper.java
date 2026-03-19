package com.example.taskmanagerback.adapter.in.task;

import com.example.taskmanagerback.adapter.in.task.dto.CreateTaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.SearchTaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.TaskDto;
import com.example.taskmanagerback.adapter.in.task.dto.UpdateTaskDto;
import com.example.taskmanagerback.adapter.repository.postgres.task.UsersRepo;
import com.example.taskmanagerback.app.api.project.GetProjectByName;
import com.example.taskmanagerback.model.task.Task;
import com.example.taskmanagerback.model.task.TimeInterval;
import com.example.taskmanagerback.model.task.constants.TaskStatus;
import com.example.taskmanagerback.model.task.constants.TaskType;
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
    UsersRepo usersRepo;

    @Mapping(target = "project", source = "task.project.name")
    @Mapping(target = "status", expression = "java(task.getStatus().getValue())")
    @Mapping(target = "type", expression = "java(task.getType().getValue())")
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
                    .map(Duration::toString)
                ).orElse(null);
    }

    @Mapping(target = "project", expression = "java(getProjectByName.execute(createTaskDto.project()))")
    @Mapping(target = "assignee", expression = "java(usersRepo.findByUsername(createTaskDto.assignee()).orElse(null))")
    @Mapping(target = "type", qualifiedByName = "getTaskTypeByValue", source = "createTaskDto.type")
    public abstract Task createTaskDtoToTask(CreateTaskDto createTaskDto);

    @Mapping(target = "assignee", expression = "java(usersRepo.findByUsername(updateTaskDto.assignee()).orElse(null))")
    @Mapping(target = "status", qualifiedByName = "getTaskStatusByValue", source = "updateTaskDto.status")
    @Mapping(target = "type", qualifiedByName = "getTaskTypeByValue", source = "updateTaskDto.type")
    public abstract Task updateTaskDtoToTask(UpdateTaskDto updateTaskDto);

    @Named("getTaskTypeByValue")
    protected TaskType getTaskTypeByValue(String value) {
        return TaskType.findTypeByValue(value);
    }

    @Named("getTaskStatusByValue")
    protected TaskStatus getTaskStatusByValue(String value) {
        return TaskStatus.findStatusByValue(value);
    }

    public abstract List<SearchTaskDto> toListOfSearchTaskDto(List<Task> tasks);

    @Mapping(target = "project", source = "task.project.name")
    @Mapping(target = "assignee", source = "task.assignee.username")
    @Mapping(target = "reporter", source = "task.reporter.username")
    protected abstract SearchTaskDto taskToSearchTaskDto(Task task);
}
