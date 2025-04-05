package com.example.taskmanagerback.app.impl.security.listener;

import com.example.taskmanagerback.app.api.security.handler.AuthSuccessEventHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthEventListener {
    AuthSuccessEventHandler authSuccessEventHandler;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        authSuccessEventHandler.handle(success);
    }
}
