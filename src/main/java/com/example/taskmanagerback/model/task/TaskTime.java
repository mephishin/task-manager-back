package com.example.taskmanagerback.model.task;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class TaskTime {
    @Id
    @Column(name = "task_key")
    String taskKey;

    @OneToMany
    @JoinColumn(name = "task_key")
    @ToString.Exclude
    List<TimeInterval> timeIntervals;

    @Temporal(TemporalType.TIMESTAMP)
    Instant created;

    @Temporal(TemporalType.TIMESTAMP)
    Instant edited;

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
        TaskTime taskTime = (TaskTime) o;
        return getTaskKey() != null && Objects.equals(getTaskKey(), taskTime.getTaskKey());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
