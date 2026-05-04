package com.example.taskmanagerback.app.api.out.postgres;

import com.example.taskmanagerback.model.users.Users;

import java.util.List;
import java.util.Optional;

public interface UsersRepo {
    Optional<Users> findById(String id);
    List<Users> findAllById(List<String> ids);
    List<Users> findAll();
}
