package com.example.taskmanagerback.adapter.in.comment;

import com.example.taskmanagerback.adapter.in.comment.dto.CommentDto;
import com.example.taskmanagerback.adapter.in.comment.dto.SaveCommentDto;
import com.example.taskmanagerback.model.task.TaskComment;
import com.example.taskmanagerback.model.users.Users;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    public abstract TaskComment commentDtoToComment(SaveCommentDto saveCommentDto);
    public abstract CommentDto commentToCommentDto(TaskComment taskComment);
    public abstract List<CommentDto> commentListToCommentDtoList(List<TaskComment> taskComment);

    public abstract CommentDto.AuthorDto userToAuthorDto(Users user);
}
