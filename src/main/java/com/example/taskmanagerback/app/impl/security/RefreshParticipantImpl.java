package com.example.taskmanagerback.app.impl.security;

import com.example.taskmanagerback.adapter.repository.postgres.task.UsersRepo;
import com.example.taskmanagerback.app.api.security.RefreshParticipant;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshParticipantImpl implements RefreshParticipant {
    UsersRepo usersRepo;

    @Override
    @Transactional
    public void execute(String id, Map<String, Object> tokenAttributes) {
        var user = usersRepo.findById(id).orElseThrow();

        user.setFirstName((String) tokenAttributes.get("first_name"));
        user.setMiddleName((String) tokenAttributes.get("middle_name"));
        user.setLastName((String) tokenAttributes.get("last_name"));
        user.setUsername((String) tokenAttributes.get("preferred_username"));
        user.setGroup((String) tokenAttributes.get("group"));
    }
}
