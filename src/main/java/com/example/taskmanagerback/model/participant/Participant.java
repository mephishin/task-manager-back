package com.example.taskmanagerback.model.participant;

import com.example.taskmanagerback.model.task.Task;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Participant {
    @Id
    String id;

    String fullname;

    @OneToMany(mappedBy = "assignee")
    List<Task> assignedTasks;

    @OneToMany(mappedBy = "reporter")
    List<Task> reportedTasks;
}
