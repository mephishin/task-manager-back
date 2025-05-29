package com.example.taskmanagerback.model.task;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class TaskTime {
    @Id
    @Column(name = "task_key")
    String taskKey;

    @OneToMany
    @JoinColumn(name = "task_key")
    List<TimeInterval> timeIntervals;

    @Temporal(TemporalType.TIMESTAMP)
    Instant created;

    @Temporal(TemporalType.TIMESTAMP)
    Instant edited;
}
