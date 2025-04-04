package com.example.taskmanagerback.model.task;

import com.example.taskmanagerback.model.participant.Participant;
import com.example.taskmanagerback.model.project.Project;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class Task {
    @Id
    String key;

    String name;

    String description;

    @ManyToOne
    @JoinColumn(name = "status_id")
    TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "type_id")
    TaskType type;

    @ManyToOne
    @JoinColumn(name = "project_key")
    Project project;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    Participant assignee;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    Participant reporter;
}
