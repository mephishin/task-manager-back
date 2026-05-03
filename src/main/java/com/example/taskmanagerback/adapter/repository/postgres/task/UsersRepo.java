package com.example.taskmanagerback.adapter.repository.postgres.task;

import com.example.taskmanagerback.model.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, String> {
    Optional<Users> findByUsername(String username);
}
