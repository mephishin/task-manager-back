package com.example.taskmanagerback.app.api.in.project;

import com.example.taskmanagerback.model.project.Project;

public interface GetProjectByName {
    Project execute(String name);
}
