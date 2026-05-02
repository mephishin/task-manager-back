package com.example.taskmanagerback.app.api.project;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface AcceptProjectInvite {
    String execute(String inviteKey, JwtAuthenticationToken jwtAuthenticationToken);
}
