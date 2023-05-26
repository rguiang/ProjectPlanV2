package com.exist.project_plan.web;

import com.exist.project_plan.model.Dto.ProjectDto;
import com.exist.project_plan.service.web.WebProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProjectEditController {
    private WebProjectService projectService;
    @Autowired
    ProjectEditController(WebProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping("/edit/{projectId}")
    public String editProject(Model model, @PathVariable("projectId") Long projectId){
        this.projectService.loadEditProject(model, projectId);
        return "editProject";
    }

    @PostMapping(value = "/edit/save/{projectId}")
    public String saveEditProject(RedirectAttributes redirect,Model model, @ModelAttribute("editedProject") ProjectDto project, @PathVariable("projectId")Long projectId){
        this.projectService.saveEditProject(model,projectId, project,redirect);
        return "redirect:/edit/" + projectId.toString();
    }
}
