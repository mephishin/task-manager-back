package com.example.taskmanagerback.app.impl.users;

import com.example.taskmanagerback.adapter.repository.postgres.task.UsersRepo;
import com.example.taskmanagerback.app.api.users.RemoveUserFromProject;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RemoveUserFromProjectImpl implements RemoveUserFromProject {
    UsersRepo usersRepo;

    @Override
    @Transactional
    public void execute(String userId) {
        var user = usersRepo.findById(userId).orElseThrow();
        user.setProject(null);
    }
}
