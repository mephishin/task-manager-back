package com.example.taskmanagerback.app.impl.task;

import com.example.taskmanagerback.adapter.out.repository.postgres.project.ProjectRepo;
import com.example.taskmanagerback.adapter.out.repository.postgres.task.TaskRepo;
import com.example.taskmanagerback.app.api.in.task.GetTasksByProject;
import com.example.taskmanagerback.app.api.out.postgres.UsersRepo;
import com.example.taskmanagerback.model.task.Task;
import com.example.taskmanagerback.model.users.Users;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetTasksByProjectImpl implements GetTasksByProject {
    TaskRepo taskRepo;
    ProjectRepo projectRepo;
    UsersRepo usersRepo;

    @Override
    @Transactional
    public List<Task> execute(String projectId) {
        var project = projectRepo.findById(projectId).orElseThrow();
        var tasks = taskRepo.findAllByProject(project);

        var assignees = tasks.stream().map(t -> t.getAssignee().getId());
        var reportees = tasks.stream().map(t -> t.getReporter().getId());

        var mapOfEnrichedUsers = usersRepo.findAllById(Stream.concat(assignees, reportees)
                        .distinct()
                        .toList()
                ).stream()
                .collect(Collectors.toMap(Users::getId, u -> u));
        return tasks.stream().peek(task -> {
            task.setAssignee(mapOfEnrichedUsers.get(task.getAssignee().getId()));
            task.setAssignee(mapOfEnrichedUsers.get(task.getReporter().getId()));
        }).toList();
    }
}
