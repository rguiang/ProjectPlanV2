package com.exist.project_plan.web;

import com.exist.project_plan.service.web.WebProjectService;
import com.exist.project_plan.service.web.WebTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProjectInfoController {
    private WebProjectService projectService;
    private WebTaskService taskService;
    @Autowired
    ProjectInfoController(WebProjectService projectService, WebTaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }
    @GetMapping("/project/info/{projectId}")
    public String projectInfo(Model model, @PathVariable("projectId") Long projectId){
        projectService.getProjectInfo(model, projectId);
        taskService.getAllTaskByProject(model,projectId);
        return "ProjectInfo";
    }
}
