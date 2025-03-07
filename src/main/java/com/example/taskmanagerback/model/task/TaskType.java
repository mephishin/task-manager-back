package com.example.taskmanagerback.model.task;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "task_type_dict")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskType {
    @Id
    String id;

    String value;
}
