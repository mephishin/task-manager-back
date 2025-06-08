package com.example.taskmanagerback.app.api.period;

import com.example.taskmanagerback.model.period.Period;
import com.example.taskmanagerback.model.project.Project;

public interface GetActivePeriodByProject {
    Period execute(Project project);
}
