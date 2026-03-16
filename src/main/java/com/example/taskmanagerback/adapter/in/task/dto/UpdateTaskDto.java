package com.example.taskmanagerback.adapter.in.task.dto;

public record UpdateTaskDto(
        String key,
        String name,
        String description,
        String status,
        String type,
        String assignee
) {}
