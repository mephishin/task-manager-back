package com.example.taskmanagerback.model.users;

import com.example.taskmanagerback.model.project.Project;
import com.example.taskmanagerback.model.task.Task;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class Users {
    @Id
    String id;

    @Transient
    String username;
    @Transient
    String firstName;
    @Transient
    String middleName;
    @Transient
    String lastName;
    @Transient
    String group;
    @Transient
    List<String> roles;

    @OneToMany(mappedBy = "assignee")
    @ToString.Exclude
    List<Task> assignedTasks;

    @OneToMany(mappedBy = "reporter")
    @ToString.Exclude
    List<Task> reportedTasks;

    @ManyToOne
    @JoinColumn(name = "project_key")
    Project project;

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Class<?> objectEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != objectEffectiveClass) {
            return false;
        }
        Users users = (Users) o;
        return getId() != null && Objects.equals(getId(), users.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
