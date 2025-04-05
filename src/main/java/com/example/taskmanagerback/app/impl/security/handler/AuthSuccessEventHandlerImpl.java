package com.example.taskmanagerback.app.impl.security.handler;

import com.example.taskmanagerback.adapter.repository.task.ParticipantRepo;
import com.example.taskmanagerback.app.api.security.handler.AuthSuccessEventHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthSuccessEventHandlerImpl implements AuthSuccessEventHandler {
    static String SUB = "sub";
    ParticipantRepo participantRepo;
//    CreateParticipant createParticipant;
//    UpdateParticipant updateParticipant;

    @Override
    public void handle(AuthenticationSuccessEvent authenticationSuccessEvent) {
        var jwtAuthenticationToken = (JwtAuthenticationToken) authenticationSuccessEvent.getAuthentication();
        var authUserId = jwtAuthenticationToken.getToken().getClaimAsString(SUB);
        var participant = participantRepo.findById(authUserId);
        participant.ifPresentOrElse(
                value -> {
                    log.info("User with id: {} and username: {} exists", authUserId, participant.get().getUsername());
                    log.info("Updating user");
                },
                () -> {
                    log.info("User with id: {} not exists", authUserId);
                    log.info("Adding user");
                }
        );
    }
}
