package com.exist.project_plan.model.Dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDto {
    private Long id;
    private String taskName;
    private String description;
    private Long projectId;
    private float duration;
    private LocalDateTime startDate;
}
