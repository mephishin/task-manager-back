package com.example.taskmanagerback.app.impl.project;

import com.example.taskmanagerback.adapter.out.repository.postgres.project.ProjectInviteRepo;
import com.example.taskmanagerback.adapter.out.repository.postgres.project.ProjectRepo;
import com.example.taskmanagerback.app.api.in.project.GetProjectInvite;
import com.example.taskmanagerback.model.project.ProjectInvite;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetProjectInviteImpl implements GetProjectInvite {
    ProjectRepo projectRepo;
    ProjectInviteRepo projectInviteRepo;

    @Override
    @Transactional
    public String execute(String projectId) {
        var project = projectRepo.findById(projectId).orElseThrow();
        var invite = projectInviteRepo.findByProjectAndUsed(project, false).orElse(null);
        if (isNull(invite)) {
            var newInvite = projectInviteRepo.save(new ProjectInvite().setProject(project).setUsed(false));
            return newInvite.getKey();
        } else {
            return invite.getKey();
        }
    }
}
