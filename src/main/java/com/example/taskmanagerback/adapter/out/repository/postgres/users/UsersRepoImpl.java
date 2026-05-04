package com.example.taskmanagerback.adapter.out.repository.postgres.users;

import com.example.taskmanagerback.app.api.out.postgres.UsersRepo;
import com.example.taskmanagerback.config.keycloak.KeycloakProperties;
import com.example.taskmanagerback.model.users.Users;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.AbstractUserRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersRepoImpl implements UsersRepo {
    KeycloakProperties keycloakProperties;

    UsersJpaRepo usersJpaRepo;
    Keycloak keycloakAdminClient;

    @Override
    public Optional<Users> findById(String id) {
        return usersJpaRepo.findById(id)
                .map(user -> {
                    var userRepresentation = keycloakAdminClient.realm(keycloakProperties.realm())
                            .users()
                            .get(id)
                            .toRepresentation();

                    return enrichUser(user, userRepresentation);
                });
    }

    @Override
    public List<Users> findAllById(List<String> ids) {
        var mapOfKeycloakUsers = keycloakAdminClient.realm(keycloakProperties.realm())
                .users()
                .search("id:" + String.join(" ", ids), null, null)
                .stream()
                .collect(Collectors.toMap(
                        UserRepresentation::getId,
                        userRepresentation -> userRepresentation
                ));

        return usersJpaRepo.findAllById(ids).stream()
                .map(users -> enrichUser(users, mapOfKeycloakUsers.get(users.getId())))
                .toList();
    }

    @Override
    public List<Users> findAll() {
        var mapOfKeycloakUsers = keycloakAdminClient.realm(keycloakProperties.realm())
                .users()
                .list()
                .stream()
                .collect(Collectors.toMap(
                        UserRepresentation::getId,
                        userRepresentation -> userRepresentation
                ));

        return usersJpaRepo.findAll().stream()
                .map(users -> enrichUser(users, mapOfKeycloakUsers.get(users.getId())))
                .toList();
    }

    private static Users enrichUser(Users user, UserRepresentation userRepresentation) {
        user.setUsername(userRepresentation.getUsername());
        user.setFirstName(userRepresentation.getFirstName());
        user.setMiddleName(getOrElse(userRepresentation, "middleName"));
        user.setLastName(userRepresentation.getLastName());
        user.setGroup(getOrElse(userRepresentation, "group"));
        user.setRoles(userRepresentation.getRealmRoles());

        return user;
    }

    private static String getOrElse(UserRepresentation userRepresentation, String key) {
        return Optional.ofNullable(userRepresentation)
                .map(AbstractUserRepresentation::getAttributes)
                .map(at -> at.getOrDefault(key, List.of()).stream().findFirst().orElse(null))
                .orElse(null);
    }
}
