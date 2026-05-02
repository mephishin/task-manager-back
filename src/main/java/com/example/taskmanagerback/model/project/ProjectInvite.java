package com.example.taskmanagerback.model.project;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String key;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @ToString.Exclude
    Project project;

    Boolean used;
}
