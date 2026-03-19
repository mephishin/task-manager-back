package com.example.taskmanagerback.app.impl.security.handler;

import com.example.taskmanagerback.adapter.repository.postgres.task.UsersRepo;
import com.example.taskmanagerback.app.api.security.RefreshParticipant;
import com.example.taskmanagerback.app.api.security.TransferParticipant;
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
    UsersRepo usersRepo;
    TransferParticipant transferParticipant;
    RefreshParticipant refreshParticipant;

    @Override
    public void handle(AuthenticationSuccessEvent authenticationSuccessEvent) {
        var jwtAuthenticationToken = (JwtAuthenticationToken) authenticationSuccessEvent.getAuthentication();
        log.info("Token of auth participant: {}", jwtAuthenticationToken.getTokenAttributes());
        var authUserId = jwtAuthenticationToken.getToken().getClaimAsString(SUB);
        var participant = usersRepo.findById(authUserId);
        participant.ifPresentOrElse(
                value -> {
                    log.info("User with id: {} and username: {} exists", authUserId, value.getUsername());
                    log.info("Updating user");
                },
                () -> {
                    log.info("User with id: {} not exists", authUserId);
                    log.info("Adding user");
                }
        );
    }
}
