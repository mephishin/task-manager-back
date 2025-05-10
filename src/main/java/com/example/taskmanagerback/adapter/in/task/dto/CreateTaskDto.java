package com.example.taskmanagerback.adapter.in.task.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CreateTaskDto {
    String name;
    String description;
    String status;
    String type;
    String project;
    String assignee;
}
