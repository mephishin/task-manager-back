package com.example.taskmanagerback.adapter.repository.postgres.period;

import com.example.taskmanagerback.model.period.Period;
import com.example.taskmanagerback.model.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodRepo extends JpaRepository<Period, String> {
    Optional<Period> getPeriodByProjectAndActive(Project project, Boolean active);
}
