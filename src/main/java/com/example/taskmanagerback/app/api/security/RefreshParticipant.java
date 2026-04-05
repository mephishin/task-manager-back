package com.example.taskmanagerback.app.api.security;

import java.util.Map;

public interface RefreshParticipant {
    void execute(String id, Map<String, Object> tokenAttributes);
}
