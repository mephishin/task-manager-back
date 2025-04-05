package com.example.taskmanagerback.app.impl.security;

import com.example.taskmanagerback.adapter.repository.task.ParticipantRepo;
import com.example.taskmanagerback.app.api.security.GetAuthParticipant;
import com.example.taskmanagerback.model.participant.Participant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GetAuthParticipantImpl implements GetAuthParticipant {
    static String SUB = "sub";
    ParticipantRepo participantRepo;

    @Override
    public Participant execute(JwtAuthenticationToken jwtAuthenticationToken) {
        return participantRepo.findById(jwtAuthenticationToken.getToken().getClaimAsString(SUB)).orElseThrow();
    }
}
