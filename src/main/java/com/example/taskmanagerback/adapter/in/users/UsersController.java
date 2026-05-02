package com.example.taskmanagerback.adapter.in.users;

import com.example.taskmanagerback.adapter.in.users.dto.UsersDto;
import com.example.taskmanagerback.adapter.repository.postgres.task.UsersRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.nonNull;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("isAuthenticated()")
@RequestMapping("/users")
public class UsersController {
    UsersMapper usersMapper;
    UsersRepo usersRepo;

    @GetMapping(params = "projectId")
    public List<UsersDto> getUsersByProject(@RequestParam String projectId) {
        return usersRepo.findAll().stream()
                .filter(user -> nonNull(user.getProject()) && user.getProject().getKey().equals(projectId))
                .map(usersMapper::usersToUsersDto)
                .toList();
    }
}
