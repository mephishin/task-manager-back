package com.example.taskmanagerback.adapter.in.task.dto;

public record SearchTaskDto(
        String key,
        String name,
        String description,
        String assignee,
        String reporter,
        String status) {}
