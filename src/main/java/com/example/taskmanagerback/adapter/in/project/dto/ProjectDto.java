package com.example.taskmanagerback.adapter.in.project.dto;

import java.util.List;

public record ProjectDto(
        String key,
        String name,
        String description,
        List<ParticipantDto> participants
) {
    public record ParticipantDto(
            String id,
            String firstName,
            String middleName,
            String lastName,
            String group
    ) {}
}

