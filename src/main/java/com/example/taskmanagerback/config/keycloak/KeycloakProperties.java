package com.example.taskmanagerback.config.keycloak;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public record KeycloakProperties(
        String url,
        String realm,
        String clientId,
        String username,
        String password
) {}
