package com.example.taskmanagerback.adapter.in.taskType;

import com.example.taskmanagerback.model.task.constants.TaskType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("isAuthenticated()")
public class TaskTypeController {
    TaskTypeMapper taskTypeMapper;

    @GetMapping("/type")
    public List<String> getTaskTypes() {
        log.info("Requested all task types");
        return taskTypeMapper.arrayOfTaskTypesToListOfTaskValues(TaskType.values());
    }
}
