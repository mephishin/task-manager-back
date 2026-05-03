package com.example.taskmanagerback.adapter.in.task.dto;

public record TaskDto(
        String key,
        String name,
        String description,
        String status,
        ProjectDto project,
        AssigneeDto assignee,
        String reporter,
        String created,
        String edited,
        String total
) {
    public record ProjectDto(
            String id,
            String name
    ) {}

    public record AssigneeDto(
            String id,
            String username,
            String firstName,
            String middleName,
            String lastName,
            String group
    ) {}
}
