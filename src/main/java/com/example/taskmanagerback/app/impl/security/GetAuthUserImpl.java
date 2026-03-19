package com.example.taskmanagerback.app.impl.security;

import com.example.taskmanagerback.adapter.repository.postgres.task.UsersRepo;
import com.example.taskmanagerback.app.api.security.GetAuthUser;
import com.example.taskmanagerback.model.users.Users;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GetAuthUserImpl implements GetAuthUser {
    static String SUB = "sub";
    UsersRepo usersRepo;

    @Override
    public Users execute(JwtAuthenticationToken jwtAuthenticationToken) {
        return usersRepo.findById(jwtAuthenticationToken.getToken().getClaimAsString(SUB)).orElseThrow();
    }
}
