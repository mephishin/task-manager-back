package com.example.taskmanagerback.app.impl.task;

import com.example.taskmanagerback.adapter.repository.task.TaskRepo;
import com.example.taskmanagerback.adapter.repository.task.TimeIntervalRepo;
import com.example.taskmanagerback.app.api.task.UpdateTaskStatus;
import com.example.taskmanagerback.model.task.TimeInterval;
import com.example.taskmanagerback.model.task.constants.TaskStatus;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.example.taskmanagerback.model.task.constants.TaskStatus.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UpdateTaskStatusImpl implements UpdateTaskStatus {
    static List<TaskStatus> taskStatusList = List.of(TO_DO, REVIEW);

    TaskRepo taskRepo;
    TimeIntervalRepo timeIntervalRepo;

    @Override
    @Transactional
    public void execute(String key, TaskStatus newStatus) {
        var task = taskRepo.findById(key).orElseThrow();

        if (IN_PROGRESS.equals(newStatus)) {
            var taskTime = task.getTaskTime();

            var timeInterval = timeIntervalRepo.save(
                    new TimeInterval()
                            .setId(UUID.randomUUID().toString())
                            .setTaskKey(key)
                            .setStarted(Instant.now())
            );

            var listOfTimeIntervals = taskTime.getTimeIntervals();
            listOfTimeIntervals.add(timeInterval);

        } else if (IN_PROGRESS.equals(task.getStatus()) && taskStatusList.contains(newStatus)) {
            var taskTime = task.getTaskTime();

            var unfinishedTimeInterval = taskTime.getTimeIntervals().stream()
                    .filter(UpdateTaskStatusImpl::nonNullStarted)
                    .filter(UpdateTaskStatusImpl::nullStopped)
                    .findFirst()
                    .orElseThrow();
            unfinishedTimeInterval.setStopped(Instant.now());
        }

        task.getTaskTime().setEdited(Instant.now());
        task.setStatus(newStatus);
    }

    private static boolean nullStopped(TimeInterval timeInterval) {
        return isNull(timeInterval.getStopped());
    }

    private static boolean nonNullStarted(TimeInterval timeInterval) {
        return nonNull(timeInterval.getStarted());
    }
}
