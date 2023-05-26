package com.exist.project_plan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.exist.project_plan.model.entity;")
@EnableJpaRepositories("com.exist.project_plan.repository")
@ComponentScan(basePackages = { "com.exist.project_plan.controller", "com.exist.project_plan.service", "com.exist.project_plan.web" })
@SpringBootApplication
public class ProjectPlanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectPlanApplication.class, args);
	}

}
