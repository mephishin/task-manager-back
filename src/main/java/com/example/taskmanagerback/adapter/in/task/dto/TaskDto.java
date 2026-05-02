package com.example.taskmanagerback.adapter.in.task.dto;

public record TaskDto(
        String key,
        String name,
        String description,
        String status,
        ProjectDto project,
        String assignee,
        String reporter,
        String created,
        String edited,
        String total
) {
    public record ProjectDto(
            String id,
            String name
    ) {}
}
