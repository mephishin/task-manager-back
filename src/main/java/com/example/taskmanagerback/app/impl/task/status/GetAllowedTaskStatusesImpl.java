package com.example.taskmanagerback.app.impl.task.status;

import com.example.taskmanagerback.adapter.repository.task.TaskRepo;
import com.example.taskmanagerback.app.api.task.GetAllowedTaskStatuses;
import com.example.taskmanagerback.model.task.constants.TaskStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GetAllowedTaskStatusesImpl implements GetAllowedTaskStatuses {
    TaskRepo taskRepo;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public List<TaskStatus> execute(String key) {
        var task = taskRepo.findById(key).orElseThrow(() -> new RuntimeException("No such task: " + key));

        try {
            var statusFlow = objectMapper.readValue(task.getProject().getStatusFlow(), new TypeReference<Map<TaskStatus, List<TaskStatus>>>() {});
            return statusFlow.get(task.getStatus()).stream().toList();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
