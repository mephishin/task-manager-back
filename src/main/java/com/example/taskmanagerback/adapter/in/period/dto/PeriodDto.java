package com.example.taskmanagerback.adapter.in.period.dto;

public record PeriodDto(
        String id,
        String project,
        String name,
        boolean active,
        String started,
        String ended
) {}
