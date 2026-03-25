package com.example.taskmanagerback.adapter.in.comment.dto;

import java.time.Instant;

public record CommentDto(
        String id,
        String taskKey,
        AuthorDto author,
        String text,
        Instant edited,
        Instant created
) {
    public record AuthorDto(String username) {}
}
