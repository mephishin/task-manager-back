package com.example.taskmanagerback.model.period;

import com.example.taskmanagerback.model.project.Project;
import com.example.taskmanagerback.model.task.Task;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    @ManyToOne
    @JoinColumn(name = "project_key")
    Project project;

    String name;

    Boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    Instant started;

    @Temporal(TemporalType.TIMESTAMP)
    Instant ended;

    @OneToMany(mappedBy = "period")
    List<Task> tasks;
}
