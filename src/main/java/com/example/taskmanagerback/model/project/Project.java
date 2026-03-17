package com.example.taskmanagerback.model.project;

import com.example.taskmanagerback.model.participant.Participant;
import com.example.taskmanagerback.model.period.Period;
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
@Accessors(chain=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String key;

    String name;

    @Column(name="task_prefix")
    String taskPrefix;

    @Column(name = "status_flow")
    String statusFlow;

    @OneToMany(mappedBy = "project")
    @ToString.Exclude
    List<Participant> participants;

    @OneToMany(mappedBy = "project")
    @ToString.Exclude
    List<Period> periods;

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
        Project project = (Project) o;
        return getKey() != null && Objects.equals(getKey(), project.getKey());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
