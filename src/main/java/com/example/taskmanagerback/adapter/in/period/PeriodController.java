package com.example.taskmanagerback.adapter.in.period;

import com.example.taskmanagerback.adapter.in.period.dto.PeriodDto;
import com.example.taskmanagerback.app.api.period.GetActivePeriodByProject;
import com.example.taskmanagerback.app.api.project.GetProjectByName;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", methods = {POST, GET, PUT, DELETE})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PeriodController {
    GetProjectByName getProjectByName;
    GetActivePeriodByProject getActivePeriodByProject;
    PeriodMapper periodMapper;

    @GetMapping(path = "/period", params = {"project", "active=true"})
    public PeriodDto getActivePeriodByProject(
            @RequestParam String project
    ) {
        log.info("Requested active period by project");
        return periodMapper.periodToPeriodDto(getActivePeriodByProject.execute(getProjectByName.execute(project)));
    }
}
