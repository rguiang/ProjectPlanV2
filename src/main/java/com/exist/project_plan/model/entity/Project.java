package com.exist.project_plan.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
public class Project {
    @Id
    @SequenceGenerator(
            name = "projects_sequence",
            sequenceName = "project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "projects_sequence"
    )
    private Long id;
    private String projectName;
    private String description;
    @Column(name = "`created_date`")
    @CreationTimestamp
    private LocalDateTime created_date;
    @ManyToOne(targetEntity = Task.class)
    @JoinColumn(name = "TASK_ID")
    private List<Task> tasks;
    private String status;
}
