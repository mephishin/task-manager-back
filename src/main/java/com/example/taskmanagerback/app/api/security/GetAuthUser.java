package com.example.taskmanagerback.app.api.security;

import com.example.taskmanagerback.model.users.Users;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface GetAuthUser {
    Users execute(JwtAuthenticationToken jwtAuthenticationToken);
}
