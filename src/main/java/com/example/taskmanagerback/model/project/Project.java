package com.example.taskmanagerback.model.project;

import com.example.taskmanagerback.model.participant.Participant;
import com.example.taskmanagerback.model.period.Period;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project {
    @Id
    String key;

    String name;

    @OneToMany(mappedBy = "project")
    List<Participant> participants;

    @OneToMany(mappedBy = "project")
    List<Period> periods;
}
