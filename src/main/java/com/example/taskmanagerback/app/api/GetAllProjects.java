package com.example.taskmanagerback.app.api;

import com.example.taskmanagerback.model.project.Project;

import java.util.List;

public interface GetAllProjects {
    List<Project> execute();
}
