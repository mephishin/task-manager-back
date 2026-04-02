package com.example.taskmanagerback.util;

import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    public static final DateTimeFormatter DATE_TIME_FORMAT_NO_SECONDS = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
}
