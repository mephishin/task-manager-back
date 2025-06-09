package com.example.taskmanagerback.adapter.in.taskType;

import com.example.taskmanagerback.model.task.constants.TaskType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", methods = {POST, GET, PUT, DELETE})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskTypeController {
    TaskTypeMapper taskTypeMapper;

    @GetMapping("/types")
    public List<String> getTaskTypes() {
        log.info("Requested all task types");
        return taskTypeMapper.arrayOfTaskTypesToListOfTaskValues(TaskType.values());
    }
}
