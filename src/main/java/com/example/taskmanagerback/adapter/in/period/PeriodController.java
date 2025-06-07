package com.example.taskmanagerback.adapter.in.period;

import com.example.taskmanagerback.model.task.constants.TaskStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", methods = {POST, GET, PUT, DELETE})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PeriodController {

    @GetMapping("/periods/statuses")
    public List<String> getTaskStatuses() {
        log.info("Requested all task statuses");
        return Arrays.stream(TaskStatus.values())
                .map(Enum::name)
                .toList();
    }
}
