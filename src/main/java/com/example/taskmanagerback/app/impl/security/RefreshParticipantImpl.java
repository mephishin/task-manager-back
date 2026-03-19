package com.example.taskmanagerback.app.impl.security;

import com.example.taskmanagerback.adapter.repository.postgres.task.UsersRepo;
import com.example.taskmanagerback.app.api.security.RefreshParticipant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshParticipantImpl implements RefreshParticipant {
    UsersRepo usersRepo;

    @Override
    public void execute(String preferredUsername, String id) {

    }
}
