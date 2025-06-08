package com.example.taskmanagerback.adapter.in.period.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PeriodDto {
    String id;

    String project;

    String name;

    Boolean active;

    String started;

    String ended;
}
