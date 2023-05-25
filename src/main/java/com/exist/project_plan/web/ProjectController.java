package com.exist.project_plan.web;

import com.exist.project_plan.model.Dto.ProjectDto;
import com.exist.project_plan.service.web.WebProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProjectController {
    private WebProjectService projectService;
    @Autowired
    ProjectController(WebProjectService projectService) {
        this.projectService = projectService;
    }
    @GetMapping("/add/project")
    public String addProject(Model model){
        return "addProject";
    }

    @PostMapping("/add/project")
    public String saveNewProject(Model model, @ModelAttribute("addProject") ProjectDto project, RedirectAttributes redirect){
        projectService.addProject(model,project,redirect);
        return "redirect:/add/project";
    }

    @PostMapping("/delete/project/{projectId}")
    public String deleteProject(Model model,@PathVariable("projectId") Long projectId, RedirectAttributes redirect){
        projectService.deleteProject(model,projectId,redirect);
        return "redirect:/";
    }
}
