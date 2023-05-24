package com.exist.project_plan.web;

import com.exist.project_plan.model.Dto.TaskDependencyDto;
import com.exist.project_plan.model.Dto.TaskDto;
import com.exist.project_plan.service.web.WebTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TaskController {
    private WebTaskService taskService;
    @Autowired
    public TaskController(WebTaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/add/task/{projectId}")
    public String addTask(Model model, @PathVariable("projectId") Long projectId, RedirectAttributes redirect) {
        redirect.addFlashAttribute("projectId", projectId);
        return "addTask";
    }

    @PostMapping("/add/task")
    public String saveTask(Model model, @ModelAttribute("task") TaskDto task, RedirectAttributes redirect) {
        taskService.addTask(model, task, redirect);
        return "redirect:/add/task/" + task.getProjectId();
    }

    @GetMapping("/edit/task/{taskId}")
    public String editTask(Model model, @PathVariable("taskId") Long taskId) {
        taskService.loadEditTask(model,taskId);
        taskService.checkTaskIfHasDependencies(model, taskId);
        return "editTask";
    }

    @PostMapping("/edit/save")
    public String saveEditTask(Model model, @ModelAttribute("editedTask") TaskDto task, RedirectAttributes redirect) {
        taskService.saveEditTask(model, task, redirect);
        return "redirect:/edit/task/" + task.getId();
    }

    @PostMapping("/delete/task/{taskId}")
    public String deleteTask(Model model,@PathVariable("taskId") Long taskId, RedirectAttributes redirect){
        return taskService.deleteTask(model, taskId, redirect);
    }

    @PostMapping("/save/dependencies")
    public String saveDependencies(Model model, @ModelAttribute("dependency") TaskDependencyDto taskDependencyDto, RedirectAttributes redirect) {
        taskService.saveDependencies(model, taskDependencyDto, redirect);
        System.out.println(taskDependencyDto.toString());
        return "redirect:/task/info/"+taskDependencyDto.getId();
    }

    @PostMapping("/delete/dependencies/{taskId}/{dependencyId}")
    public String deleteDependencies(Model model,@PathVariable("taskId") Long taskId,@PathVariable("dependencyId") Long dependencyId , RedirectAttributes redirect) {
        taskService.deleteDependency(model, taskId, dependencyId, redirect);
        return "redirect:/task/info/"+taskId;
    }
}
