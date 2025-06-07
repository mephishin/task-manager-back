package com.example.taskmanagerback.adapter.in.task.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TasksPageDto {
    Period period;
    List<Participant> participants;
    List<Participant.Task> notAssignedTasks;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder
    public static class Period {
        String name;
        String started;
        String ended;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder
    public static class Participant {
        String username;
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
}
