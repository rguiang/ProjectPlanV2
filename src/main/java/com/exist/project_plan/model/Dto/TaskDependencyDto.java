package com.exist.project_plan.model.Dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskDependencyDto {
    private Long id;
    private List<Long> dependencies;
}
