package com.example.taskmanagerback.app.api.in.security;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface GetJwtAuthenticationToken {
    JwtAuthenticationToken execute();
}
