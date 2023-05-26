package com.exist.project_plan.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;
    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project_id")
    private Project project;
    private String taskName;
    private String description;
    private float duration;
    @Column(name = "`created_date`")
    @CreationTimestamp
    private LocalDateTime created_date;

    @ManyToMany(targetEntity = Task.class)
    @JoinColumn(name = "TASK_ID")
    private List<Task> dependencies;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
