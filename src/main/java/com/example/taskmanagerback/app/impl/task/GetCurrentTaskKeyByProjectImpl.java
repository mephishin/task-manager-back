package com.example.taskmanagerback.app.impl.task;

import com.example.taskmanagerback.adapter.repository.project.ProjectRepo;
import com.example.taskmanagerback.adapter.repository.task.TaskRepo;
import com.example.taskmanagerback.app.api.task.GetCurrentTaskKeyByProject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Comparator;

import static java.lang.Long.parseLong;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetCurrentTaskKeyByProjectImpl implements GetCurrentTaskKeyByProject {
    TaskRepo taskRepo;
    ProjectRepo projectRepo;

    @Override
    public String execute(String projectName) {
        var shortedName = projectName.replaceAll("[aeiouy]", "").toUpperCase();
        var lastId = taskRepo.findAllByProject(
                projectRepo.findByName(projectName).orElseThrow()
                ).stream()
                .map(task -> parseLong(
                        task.getKey().replaceFirst("\\w+-", "")
                ))
                .max(Comparator.naturalOrder())
                .orElseThrow();
        return shortedName + "-" + ++lastId;
    }
}
