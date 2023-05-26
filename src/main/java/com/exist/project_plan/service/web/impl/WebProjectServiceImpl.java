package com.exist.project_plan.service.web.impl;

import com.exist.project_plan.model.Dto.ProjectDto;
import com.exist.project_plan.model.entity.Project;
import com.exist.project_plan.model.entity.Task;
import com.exist.project_plan.repository.ProjectRepository;
import com.exist.project_plan.repository.TaskRepository;
import com.exist.project_plan.service.web.WebProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class WebProjectServiceImpl implements WebProjectService {
    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    @Autowired
    public WebProjectServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }
    @Override
    public void loadEditProject(Model model, Long projectId) {
        editProject(model, projectId);
    }

    @Override
    public void saveEditProject(Model model, Long projectId, ProjectDto editedProject, RedirectAttributes redirect) {
        try {
            Project project = projectRepository.findById(projectId).get();
            project.setProjectName(editedProject.getProjectName());
            project.setDescription(editedProject.getDescription());
            projectRepository.save(project);
            redirect.addFlashAttribute("editStatus","success");
            model.addAttribute("project", project);
        } catch (Exception e) {
            redirect.addFlashAttribute("editStatus","failed");
        }
    }

    @Override
    public void addProject(Model model, ProjectDto project, RedirectAttributes redirect) {
        try {
            Project newProject = new Project();
            newProject.setProjectName(project.getProjectName());
            newProject.setDescription(project.getDescription());
            projectRepository.save(newProject);

            redirect.addFlashAttribute("addProjectStatus","success");
        } catch (Exception e) {
            redirect.addFlashAttribute("addProjectStatus","failed");
        }
    }

    @Override
    public void deleteProject(Model model, Long projectId, RedirectAttributes redirect) {
        try{
            projectRepository.deleteById(projectId);
            redirect.addFlashAttribute("deletedProject","success");
        } catch (Exception e) {
            redirect.addFlashAttribute("deletedProject","failed");

        }
    }

    @Override
    public void getProjectInfo(Model model, Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        List<Task> task = taskRepository.findByProjectId(projectId);

        Comparator<Task> taskComparator = (task1, task2) -> task2.getEndDate().compareTo(task1.getEndDate());
        Collections.sort(task,taskComparator);

        //LocalDateTime estematedEndDate = task.get(0).getEndDate() == null ? "" : ask.get(0).getEndDate();
        //LocalDateTime projectDateStarted = task.get(task.size()-1).getStartDate();

        model.addAttribute("projectInfo", project.get());
        try{
            model.addAttribute("EstimatedEndDate", task.get(0).getEndDate());
        } catch (Exception e) {
            model.addAttribute("EstimatedEndDate", " ");
        }

        try{
            model.addAttribute("ProjectDateStarted", task.get(task.size()-1).getStartDate());
        } catch (Exception e) {
            model.addAttribute("ProjectDateStarted", " ");
        }


        //model.addAttribute("ProjectDateStarted", projectDateStarted);
        //model.addAttribute("EstimatedEndDate", "");
    }

    public void editProject(Model model, Long projectId) {
        Project project = projectRepository.findById(projectId).get();
        model.addAttribute("project", project);

    }
}
