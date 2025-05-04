package com.example.taskmanagerback.model.participant;

import com.example.taskmanagerback.model.project.Project;
import com.example.taskmanagerback.model.task.Task;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class Participant {
    @Id
    String id;

    String username;

    @OneToMany(mappedBy = "assignee")
    List<Task> assignedTasks;

    @OneToMany(mappedBy = "reporter")
    List<Task> reportedTasks;

    @ManyToOne
    @JoinColumn(name = "project_key")
    Project project;
}
