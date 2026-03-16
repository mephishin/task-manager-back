package com.example.taskmanagerback.adapter.in.task.dto;

public record CreateTaskDto(
        String name,
        String description,
        String status,
        String type,
        String project,
        String assignee
) {}
