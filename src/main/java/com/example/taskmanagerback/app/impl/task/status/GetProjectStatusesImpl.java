package com.example.taskmanagerback.app.impl.task.status;

import com.example.taskmanagerback.adapter.repository.project.ProjectRepo;
import com.example.taskmanagerback.app.api.task.status.GetProjectStatuses;
import com.example.taskmanagerback.model.task.constants.TaskStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GetProjectStatusesImpl implements GetProjectStatuses {
    ProjectRepo projectRepo;
    ObjectMapper objectMapper;

    @Override
    public List<TaskStatus> execute(String projectId) {
        var project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("No such project: " + projectId));
        try {
            return objectMapper.readValue(project.getStatuses(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
