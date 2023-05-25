package com.exist.project_plan.controller;

import com.exist.project_plan.model.entity.Project;
import com.exist.project_plan.model.enums.ResponseStatus;
import com.exist.project_plan.model.response.ResponseDTO;
import com.exist.project_plan.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class ProjectApi {
    private ProjectService projectService;
    @Autowired
    public ProjectApi(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/getAllProjects")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
    @PostMapping("/{projectId}")
    public Project getProject(@PathVariable("projectId") Long projectId) {
        return projectService.getProject(projectId).get();
    }

    @PostMapping("/createProject")
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PostMapping("/delete/{projectId}")
    public Project deleteProject(@PathVariable("projectId") Long projectId) {
        return projectService.deleteProject(projectId);
    }

}
