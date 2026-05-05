package com.example.taskmanagerback.adapter.out.repository.postgres.users;

import com.example.taskmanagerback.app.api.out.postgres.UsersRepo;
import com.example.taskmanagerback.config.keycloak.KeycloakProperties;
import com.example.taskmanagerback.model.users.Users;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.AbstractUserRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersRepoImpl implements UsersRepo {
    KeycloakProperties keycloakProperties;

    UsersJpaRepo usersJpaRepo;
    Keycloak keycloakAdminClient;

    @Override
    @Transactional
    public Optional<Users> findById(String id) {
        var userRepresentation = keycloakAdminClient.realm(keycloakProperties.realm())
                .users()
                .get(id)
                .toRepresentation();

        if (isNull(userRepresentation)) {
            return Optional.empty();
        }

        var user = usersJpaRepo.findById(id);

        return user.map(users -> enrichUser(users, userRepresentation)).or(() -> Optional.of(enrichUser(
                usersJpaRepo.save(new Users().setId(userRepresentation.getId())), userRepresentation)));
    }

    @Override
    @Transactional
    public List<Users> findAllById(List<String> ids) {
        var listOfKeycloakUsers = keycloakAdminClient.realm(keycloakProperties.realm())
                .users()
                .search("id:" + String.join(" ", ids), null, null);

        var mapOfUsers =  usersJpaRepo.findAllById(ids).stream()
                .collect(Collectors.toMap(Users::getId, users -> users));

        enrichMapOfUsers(listOfKeycloakUsers, mapOfUsers);

        return mapOfUsers.values().stream().toList();
    }

    @Override
    @Transactional
    public List<Users> findAll() {
        var listOfKeycloakUsers = keycloakAdminClient.realm(keycloakProperties.realm())
                .users()
                .list();

        var mapOfUsers =  usersJpaRepo.findAll().stream().collect(Collectors.toMap(Users::getId, users -> users));

        enrichMapOfUsers(listOfKeycloakUsers, mapOfUsers);

        return mapOfUsers.values().stream().toList();

    }

    private void enrichMapOfUsers(List<UserRepresentation> listOfKeycloakUsers, Map<String, Users> mapOfUsers) {
        for (var keycloakUser: listOfKeycloakUsers) {
            mapOfUsers.computeIfAbsent(
                    keycloakUser.getId(),
                    key -> usersJpaRepo.save(new Users().setId(key)));
            mapOfUsers.computeIfPresent(
                    keycloakUser.getId(),
                    (id, user) -> enrichUser(user, keycloakUser));
        }
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
