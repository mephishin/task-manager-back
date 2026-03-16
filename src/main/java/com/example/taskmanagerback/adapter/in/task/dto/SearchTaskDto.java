package com.example.taskmanagerback.adapter.in.task.dto;

public record SearchTaskDto(
        String key,
        String name,
        String description,
        String project,
        String assignee,
        String reporter
) {}
