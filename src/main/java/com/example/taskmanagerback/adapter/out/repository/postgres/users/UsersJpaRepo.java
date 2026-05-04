package com.example.taskmanagerback.adapter.out.repository.postgres.users;

import com.example.taskmanagerback.model.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersJpaRepo extends JpaRepository<Users, String> {
}
