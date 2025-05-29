package com.example.taskmanagerback.model.task;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class TimeInterval {
    @Id
    @Column(name = "id")
    String id;

    @Column(name = "task_key")
    String taskKey;

    @Temporal(TemporalType.TIMESTAMP)
    Instant started;

    @Temporal(TemporalType.TIMESTAMP)
    Instant stopped;
}
