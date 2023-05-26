package com.exist.project_plan.service;

import com.exist.project_plan.model.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<Project> getAllProjects ();
    Optional<Project> getProject(Long projectId);
    void editProject(Long projectId);
    Project createProject(Project project);
    Project deleteProject(Long projectId);
}
