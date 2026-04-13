package com.example.taskmanagerback.model.task;

import com.example.taskmanagerback.model.period.Period;
import com.example.taskmanagerback.model.project.Project;
import com.example.taskmanagerback.model.task.constants.TaskStatus;
import com.example.taskmanagerback.model.users.Users;
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
public class Task {
    @Id
    String key;

    String name;

    String description;

    @Enumerated(EnumType.STRING)
    TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "project_key")
    Project project;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    Users assignee;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    Users reporter;

    @ManyToOne
    @JoinColumn(name = "period_id")
    Period period;

    @OneToMany
    @JoinColumn(name = "task_key")
    @ToString.Exclude
    List<TaskComment> taskComments;

    @OneToOne
    @PrimaryKeyJoinColumn
    TaskTime taskTime;

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
        Task task = (Task) o;
        return getKey() != null && Objects.equals(getKey(), task.getKey());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
