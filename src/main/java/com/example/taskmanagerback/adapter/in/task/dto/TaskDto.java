package com.example.taskmanagerback.adapter.in.task.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Accessors(chain = true)
public class TaskDto {
    String key;
    String name;
    String description;
    String status;
    String type;
    String project;
    String assignee;
    String reporter;
    String created;
    String edited;
}
