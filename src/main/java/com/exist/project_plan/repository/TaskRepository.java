package com.exist.project_plan.repository;

import com.exist.project_plan.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByProjectId(Long project_id);
    List<Task> findByDependencies(Task task);
}
