package com.example.taskmanagerback.app.api.in.security.handler;

import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

public interface AuthSuccessEventHandler {
    void handle(AuthenticationSuccessEvent authenticationSuccessEvent);
}
