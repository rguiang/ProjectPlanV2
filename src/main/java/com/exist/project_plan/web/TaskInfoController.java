package com.exist.project_plan.web;

import com.exist.project_plan.service.web.WebProjectService;
import com.exist.project_plan.service.web.WebTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TaskInfoController {
    private WebTaskService webTaskService;
    private WebProjectService webProjectService;
    @Autowired
    public TaskInfoController(WebTaskService webTaskService, WebProjectService webProjectService) {
        this.webTaskService = webTaskService;
        this.webProjectService = webProjectService;
    }

    @GetMapping("/task/info/{taskId}")
    public String getTaskInfo(Model model, @PathVariable("taskId") Long taskId) {
        webTaskService.getTaskDetails(model, taskId);
        return "TaskInfo";
    }

    @GetMapping("/add/task/dependency/{taskId}/{projectId}")
    public String addTaskDependency(Model model, @PathVariable("taskId") Long taskId, @PathVariable("projectId") Long projectId) {
        webTaskService.getTaskDetails(model, taskId);
        webTaskService.getAllTaskByProjectAddDependencies(model,projectId, taskId);
        return "addTaskDependency";
    }
}
