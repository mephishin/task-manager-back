package com.example.taskmanagerback.app.api.security;

import com.example.taskmanagerback.model.participant.Participant;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface GetAuthParticipant {
    Participant execute(JwtAuthenticationToken jwtAuthenticationToken);
}
