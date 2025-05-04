package com.example.taskmanagerback.app.impl.security;

import com.example.taskmanagerback.adapter.repository.task.ParticipantRepo;
import com.example.taskmanagerback.app.api.security.TransferParticipant;
import com.example.taskmanagerback.model.participant.Participant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransferParticipantImpl implements TransferParticipant {
    ParticipantRepo participantRepo;

    @Override
    public void execute(String preferredUsername, String id) {
        participantRepo.save(new Participant()
                .setUsername(preferredUsername)
                .setId(id)
        );
    }
}
