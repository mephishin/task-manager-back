package com.example.taskmanagerback.adapter.in.tasksChart.dto;

import java.util.List;

public record TasksChartDto(
        Period period,
        List<Participant> participants,
        List<Participant.Task> notAssignedTasks
) {
    public record Period(
            String name,
            String started,
            String ended
    ) {}

    public record Participant(
            String username,
            String firstName,
            String middleName,
            String lastName,
            String group,
            List<Task> tasks
    ) {
        public record Task(
                String key,
                String name,
                String type,
                String status
        ) {}
    }
}


