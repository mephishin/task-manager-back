package com.example.taskmanagerback.adapter.in.task.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListOfTasksDto {
    List<Task> tasks;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder
    public static class Task {
        String key;
        String name;
        String type;
        String status;
    }
}
