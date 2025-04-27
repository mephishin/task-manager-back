package com.example.taskmanagerback.adapter.in.participant;

import com.example.taskmanagerback.adapter.in.participant.dto.ParticipantDto;
import com.example.taskmanagerback.adapter.repository.task.ParticipantRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", methods = {POST, GET, PUT})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
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
