package com.exist.project_plan.service.web;

import com.exist.project_plan.model.Dto.ProjectDto;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface WebProjectService {
    void loadEditProject(Model model, Long projectId);
    void saveEditProject(Model model, Long projectId, ProjectDto project, RedirectAttributes redirect);
    void addProject(Model model, ProjectDto project, RedirectAttributes redirect);
    void deleteProject(Model model, Long projectId, RedirectAttributes redirect);
    void getProjectInfo(Model model, Long projectId);
}
