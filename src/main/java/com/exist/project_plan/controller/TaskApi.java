package com.exist.project_plan.controller;

import com.exist.project_plan.model.entity.Task;
import com.exist.project_plan.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/task")
public class TaskApi {
    private TaskService taskService;
    @Autowired
    public  TaskApi(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping
    public List<Task> getTask() {
        return null;
    }
}
