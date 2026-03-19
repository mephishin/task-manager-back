package com.example.taskmanagerback.app.impl.period;

import com.example.taskmanagerback.adapter.repository.postgres.period.PeriodRepo;
import com.example.taskmanagerback.app.api.period.GetActivePeriodByProject;
import com.example.taskmanagerback.model.period.Period;
import com.example.taskmanagerback.model.project.Project;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetActivePeriodByProjectImpl implements GetActivePeriodByProject {
    PeriodRepo periodRepo;

    @Override
    public Period execute(Project project) {
        return periodRepo.getPeriodByProjectAndActive(project, true).orElseThrow();
    }
}
