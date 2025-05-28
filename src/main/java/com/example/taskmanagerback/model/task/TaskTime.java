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
public class TaskTime {
    @Id
    @Column(name = "task_key")
    String id;

    @Temporal(TemporalType.TIMESTAMP)
    Instant created;

    @Temporal(TemporalType.TIMESTAMP)
    Instant edited;
}
