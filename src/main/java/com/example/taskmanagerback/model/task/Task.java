package com.example.taskmanagerback.model.task;

import com.example.taskmanagerback.model.users.Users;
import com.example.taskmanagerback.model.period.Period;
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
    Users assignee;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    Users reporter;

    @ManyToOne
    @JoinColumn(name = "period_id")
    Period period;

    @OneToOne
    @PrimaryKeyJoinColumn
    TaskTime taskTime;
}
