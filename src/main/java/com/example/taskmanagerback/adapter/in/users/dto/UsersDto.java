package com.example.taskmanagerback.adapter.in.users.dto;

public record UsersDto(
        String id,
        String username,
        String firstName,
        String middleName,
        String lastName,
        String group,
        String project
) {}
