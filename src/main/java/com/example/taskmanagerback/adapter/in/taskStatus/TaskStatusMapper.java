package com.example.taskmanagerback.adapter.in.taskStatus;

import com.example.taskmanagerback.model.task.constants.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskStatusMapper {
    public List<String> arrayOfTaskStatusToListOfTaskStatusValues(List<TaskStatus> taskStatuses) {
        return taskStatuses.stream().map(TaskStatus::getValue).toList();
    }

    public TaskStatus taskStatusValueToTaskStatus(String value) {
        return TaskStatus.findStatusByValue(value);
    }
}
