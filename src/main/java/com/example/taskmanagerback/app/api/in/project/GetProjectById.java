package com.example.taskmanagerback.app.api.in.project;

import com.example.taskmanagerback.model.project.Project;

public interface GetProjectById {
    Project execute(String id);
}
