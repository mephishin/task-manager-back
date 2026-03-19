package com.example.taskmanagerback.adapter.in.users;

import com.example.taskmanagerback.adapter.in.users.dto.UsersDto;
import com.example.taskmanagerback.adapter.repository.postgres.task.UsersRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("isAuthenticated()")
public class UsersController {
    UsersMapper usersMapper;
    UsersRepo usersRepo;

    @GetMapping("/users")
    public List<UsersDto> getUsers() {
        return usersRepo.findAll().stream()
                .map(usersMapper::usersToUsersDto)
                .toList();
    }
}
