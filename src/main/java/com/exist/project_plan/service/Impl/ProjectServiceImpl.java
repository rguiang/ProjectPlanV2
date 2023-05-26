package com.exist.project_plan.service.Impl;

import com.exist.project_plan.model.entity.Project;
import com.exist.project_plan.repository.ProjectRepository;
import com.exist.project_plan.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;
    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProject(Long projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public void editProject(Long projectId) {

    }

    @Override
    public Project createProject(Project project) {
        projectRepository.save(project);
        return project;
    }

    @Override
    public Project deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId).get();
        projectRepository.deleteById(projectId);
        return project;
    }
}
