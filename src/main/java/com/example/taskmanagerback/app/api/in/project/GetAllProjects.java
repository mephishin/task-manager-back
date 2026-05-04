package com.example.taskmanagerback.app.api.in.project;

import com.example.taskmanagerback.model.project.Project;

import java.util.List;

public interface GetAllProjects {
    List<Project> execute();
}
