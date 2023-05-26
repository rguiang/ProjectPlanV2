package com.exist.project_plan.service.web;

import com.exist.project_plan.model.Dto.TaskDependencyDto;
import com.exist.project_plan.model.Dto.TaskDto;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface WebTaskService {
    void loadEditTask(Model model, Long taskId);
    void addTask(Model model, TaskDto taskDto, RedirectAttributes redirect);
    void getAllTaskByProject(Model model, Long projectId);
    void getAllTaskByProjectAddDependencies(Model model, Long projectId, Long taskId);
    void saveEditTask(Model model, TaskDto taskDto, RedirectAttributes redirect);
    String deleteTask(Model model, Long taskId, RedirectAttributes redirect);
    void getTaskDetails(Model model, Long taskId);
    void checkTaskIfHasDependencies(Model model, Long taskId);
    void saveDependencies(Model model, TaskDependencyDto taskDependencyDto, RedirectAttributes redirect);
    void deleteDependency(Model model, Long taskId, Long dependencyId,  RedirectAttributes redirect);
}
