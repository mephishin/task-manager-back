package com.example.taskmanagerback.model.task.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TaskStatus {
    TO_DO("К ВЫПОЛНЕНИЮ"),
    IN_PROGRESS("В ПРОГРЕССЕ"),
    REVIEW("К ПРОВЕРКЕ"),
    IN_REVIEW("НА ПРОВЕРКЕ"),
    DONE("ВЫПОЛНЕНА"),
    CLOSED("ЗАКРЫТА");

    String value;

    public static TaskStatus findStatusByValue(String value) {
        return Arrays.stream(TaskStatus.values())
                .filter(taskStatus -> taskStatus.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No status with value: " + value));
    }
}
