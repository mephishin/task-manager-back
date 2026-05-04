package com.example.taskmanagerback.app.impl.security;

import com.example.taskmanagerback.app.api.in.security.GetJwtAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class GetJwtAuthenticationTokenImpl implements GetJwtAuthenticationToken {
    @Override
    public JwtAuthenticationToken execute() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtAuthenticationToken) authentication;
    }
}
