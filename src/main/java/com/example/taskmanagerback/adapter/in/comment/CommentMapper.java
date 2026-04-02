package com.example.taskmanagerback.adapter.in.comment;

import com.example.taskmanagerback.adapter.in.comment.dto.CommentDto;
import com.example.taskmanagerback.model.task.TaskComment;
import com.example.taskmanagerback.model.users.Users;
import com.example.taskmanagerback.util.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Mapping(target = "created", qualifiedByName = "getFormattedInstant", source = "taskComment.created")
    @Mapping(target = "edited", qualifiedByName = "getFormattedInstant", source = "taskComment.edited")
    public abstract CommentDto commentToCommentDto(TaskComment taskComment);
    public abstract List<CommentDto> commentListToCommentDtoList(List<TaskComment> taskComment);

    public abstract CommentDto.AuthorDto userToAuthorDto(Users user);

    @Named("getFormattedInstant")
    protected String getFormattedInstant(Instant instant) {
        return Optional.ofNullable(instant)
                .map(CommentMapper::formatInstant)
                .orElse(null);
    }

    private static String formatInstant(Instant value) {
        return DateTimeUtils.DATE_TIME_FORMAT_NO_SECONDS.format(value.atZone(ZoneId.of("Europe/Moscow")));
    }
}
