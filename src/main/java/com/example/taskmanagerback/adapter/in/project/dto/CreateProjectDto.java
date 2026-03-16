package com.example.taskmanagerback.adapter.in.project.dto;

import java.util.List;

public record CreateProjectDto(
        String name,
        String description,
        List<String> participants,
        String taskPrefix
) {}
