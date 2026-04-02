package com.example.taskmanagerback.adapter.in.comment.dto;

public record CommentDto(
        String id,
        String taskKey,
        AuthorDto author,
        String text,
        String edited,
        String created
) {
    public record AuthorDto(String username) {}
}
