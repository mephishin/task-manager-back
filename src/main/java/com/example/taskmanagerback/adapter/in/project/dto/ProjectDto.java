package com.example.taskmanagerback.adapter.in.project.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProjectDto {
    String key;
    String name;
}
