package com.example.taskmanagerback.adapter.in.task.dto;

public record CreateTaskDto(
        String name,
        String description,
        String type,
        String project,
        String assignee
) {}
