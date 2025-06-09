package com.example.taskmanagerback.app.impl.task;

import com.example.taskmanagerback.adapter.repository.task.TaskRepo;
import com.example.taskmanagerback.app.api.task.GetAllowedTaskStatuses;
import com.example.taskmanagerback.model.task.constants.TaskStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.example.taskmanagerback.model.task.constants.TaskStatus.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAllowedTaskStatusesImpl implements GetAllowedTaskStatuses {
    static Map<TaskStatus, List<TaskStatus>> TRANSITIONS = Map.of(
            TO_DO, List.of(IN_PROGRESS, CLOSED),
            IN_PROGRESS, List.of(REVIEW, CLOSED, TO_DO),
            REVIEW, List.of(IN_REVIEW, CLOSED),
            IN_REVIEW, List.of(TO_DO, DONE, CLOSED),
            DONE, List.of(TO_DO),
            CLOSED, List.of(TO_DO)
    );

    TaskRepo taskRepo;

    @Override
    public List<TaskStatus> execute(String key) {
        var task = taskRepo.findById(key).orElseThrow();

        return TRANSITIONS.get(task.getStatus()).stream().toList();
    }
}
