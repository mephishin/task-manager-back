package com.example.taskmanagerback.adapter.in.project.dto;

public record CreateProjectDto(
        String name,
        String description,
        String leader,
        String taskPrefix
) {}
