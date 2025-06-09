package com.example.taskmanagerback.adapter.in.taskType;

import com.example.taskmanagerback.model.task.constants.TaskType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TaskTypeMapper {
    public List<String> arrayOfTaskTypesToListOfTaskValues(TaskType[] taskTypes) {
        return Arrays.stream(taskTypes).map(TaskType::getValue).toList();
    }
}
