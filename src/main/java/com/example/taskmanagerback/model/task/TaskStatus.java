package com.example.taskmanagerback.model.task;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "task_status_dict")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskStatus {
    @Id
    String id;

    String value;

}
