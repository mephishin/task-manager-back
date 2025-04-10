package com.example.taskmanagerback.adapter.in.participant.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ParticipantDto {
    String id;
    String username;
    String project;
}
