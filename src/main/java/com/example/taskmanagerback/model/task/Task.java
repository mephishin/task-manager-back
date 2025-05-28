package com.example.taskmanagerback.model.task;

import com.example.taskmanagerback.model.participant.Participant;
import com.example.taskmanagerback.model.project.Project;
import com.example.taskmanagerback.model.task.constants.TaskStatus;
import com.example.taskmanagerback.model.task.constants.TaskType;
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

    @Enumerated(EnumType.STRING)
    TaskStatus status;

    @Enumerated(EnumType.STRING)
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

    @OneToOne
    @PrimaryKeyJoinColumn
    TaskTime taskTime;
}
