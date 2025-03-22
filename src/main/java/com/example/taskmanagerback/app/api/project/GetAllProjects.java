package com.example.taskmanagerback.app.api.project;

import com.example.taskmanagerback.model.project.Project;

import java.util.List;

public interface GetAllProjects {
    List<Project> execute();
}
