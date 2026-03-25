package com.example.taskmanagerback.adapter.in.period;

import com.example.taskmanagerback.adapter.in.period.dto.PeriodDto;
import com.example.taskmanagerback.adapter.repository.postgres.project.ProjectRepo;
import com.example.taskmanagerback.app.api.period.GetActivePeriodByProject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("isAuthenticated()")
public class PeriodController {
    ProjectRepo projectRepo;
    GetActivePeriodByProject getActivePeriodByProject;
    PeriodMapper periodMapper;

    @GetMapping(path = "/period", params = {"projectId", "active=true"})
    public PeriodDto getActivePeriodByProject(
            @RequestParam String projectId
    ) {
        log.info("Requested active period by project");
        return periodMapper.periodToPeriodDto(
                getActivePeriodByProject.execute(
                        projectRepo.findById(projectId)
                                .orElseThrow(() -> new RuntimeException("No such a project with id: " + projectId))));
    }
}
