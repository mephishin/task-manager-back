package com.example.taskmanagerback.model.task.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TaskType {
    TASK("Task");

    String value;

    public static TaskType findTypeByValue(String value) {
        return Arrays.stream(TaskType.values())
                .filter(taskType -> taskType.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No type with value: " + value));
    }
}
