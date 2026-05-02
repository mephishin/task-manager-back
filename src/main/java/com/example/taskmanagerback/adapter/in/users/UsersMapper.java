package com.example.taskmanagerback.adapter.in.users;

import com.example.taskmanagerback.adapter.in.users.dto.UsersDto;
import com.example.taskmanagerback.model.users.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UsersMapper {
    @Mapping(target="project", source = "users.project.key")
    public abstract UsersDto usersToUsersDto(Users users);
}
