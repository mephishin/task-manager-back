package com.example.taskmanagerback.model.users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum UserRole {
    ADMIN("task-manager_admin"),
    PARTICIPANT("task-manager_participant"),
    LEADER("task-manager_leader");

    String value;

    public static UserRole findRoleByValue(String value) {
        return Arrays.stream(UserRole.values())
                .filter(role -> role.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No role with value: " + value));
    }

    public static List<String> getValues() {
        return Arrays.stream(values()).map(UserRole::getValue).toList();
    }

    @Override
    public String toString() {
        return getValue();
    }
}
