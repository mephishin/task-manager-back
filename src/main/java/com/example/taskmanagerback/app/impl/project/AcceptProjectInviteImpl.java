package com.example.taskmanagerback.app.impl.project;

import com.example.taskmanagerback.adapter.repository.postgres.project.ProjectInviteRepo;
import com.example.taskmanagerback.app.api.project.AcceptProjectInvite;
import com.example.taskmanagerback.app.api.security.GetAuthUser;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AcceptProjectInviteImpl implements AcceptProjectInvite {
    ProjectInviteRepo projectInviteRepo;
    GetAuthUser getAuthUser;

    @Override
    @Transactional
    public String execute(String inviteKey, JwtAuthenticationToken jwtAuthenticationToken) {
        var invite = projectInviteRepo.findById(inviteKey).orElseThrow();
        if (invite.getUsed() == false) {
            var project = invite.getProject();
            var user = getAuthUser.execute(jwtAuthenticationToken);

            if (nonNull(user.getProject())) {
                throw new RuntimeException("User already have a project");
            }

            user.setProject(project);

            invite.setUsed(true);

            return invite.getProject().getKey();
        } else {
            throw new RuntimeException("Invite already used");
        }
    }
}
