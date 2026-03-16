package com.example.taskmanagerback.adapter.in.participant;

import com.example.taskmanagerback.adapter.in.participant.dto.ParticipantDto;
import com.example.taskmanagerback.adapter.repository.task.ParticipantRepo;
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
public class ParticipantController {
    ParticipantMapper participantMapper;
    ParticipantRepo participantRepo;

    @GetMapping("/participants")
    public List<ParticipantDto> getParticipants() {
        return participantRepo.findAll().stream()
                .map(participantMapper::participantToParticipantDto)
                .toList();
    }
}
