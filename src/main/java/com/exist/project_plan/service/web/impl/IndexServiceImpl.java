package com.exist.project_plan.service.web.impl;

import com.exist.project_plan.model.entity.Project;
import com.exist.project_plan.repository.ProjectRepository;
import com.exist.project_plan.service.ProjectService;
import com.exist.project_plan.service.web.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
@Service
public class IndexServiceImpl implements IndexService {

    private ProjectService projectService;
    private ProjectRepository projectRepository;
    @Autowired
    public IndexServiceImpl(ProjectService projectService,
                            ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }
    @Override
    public void loadIndex(Model model) {
        getAllProjects(model);
    }

    private void getAllProjects(Model model) {
        try {
            List<Project> projectPage = projectRepository.findAll();
            model.addAttribute("projects", projectPage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
