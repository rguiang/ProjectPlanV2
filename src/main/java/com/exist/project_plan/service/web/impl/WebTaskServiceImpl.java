package com.exist.project_plan.service.web.impl;

import com.exist.project_plan.model.Dto.TaskDependencyDto;
import com.exist.project_plan.model.Dto.TaskDto;
import com.exist.project_plan.model.entity.Project;
import com.exist.project_plan.model.entity.Task;
import com.exist.project_plan.repository.ProjectRepository;
import com.exist.project_plan.repository.TaskRepository;
import com.exist.project_plan.service.web.WebTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebTaskServiceImpl implements WebTaskService {
    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    @Autowired
    WebTaskServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void loadEditTask(Model model, Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        model.addAttribute("task", task);
        model.addAttribute("projectId", task.getProject().getId());
    }

    @Override
    public void addTask(Model model, TaskDto taskDto, RedirectAttributes redirect) {
        try {
            Project project = projectRepository.findById(taskDto.getProjectId()).get();
            Task task = new Task();
            task.setTaskName(taskDto.getTaskName());
            task.setDescription(taskDto.getDescription());
            task.setProject(project);
            task.setDuration(taskDto.getDuration());

            taskRepository.save(task);
            redirect.addFlashAttribute("addTaskStatus","success");
        } catch (Exception e) {
            redirect.addFlashAttribute("addTaskStatus","failed");
        }
    }

    @Override
    public void getAllTaskByProject(Model model, Long projectId) {
        List<Task> task = taskRepository.findByProjectId(projectId);
        model.addAttribute("projectTask", task);
    }

    @Override
    public void getAllTaskByProjectAddDependencies(Model model, Long projectId, Long taskId) {

        List<Task> tasks = taskRepository.findByProjectId(projectId);
        Task task = taskRepository.findById(taskId).get();
        List<Task> toBeRemove=new ArrayList<>();
        for(Task displayTask: tasks) {
            for(Task dependencies: task.getDependencies()) {
                if(displayTask.getId().equals(dependencies.getId())) {
                    toBeRemove.add(displayTask);
                }
            }
        }
        for(Task taskToRemove: toBeRemove){
            tasks.remove(taskToRemove);
        }
        model.addAttribute("projectTask", tasks);
    }

    @Override
    public void saveEditTask(Model model, TaskDto taskDto, RedirectAttributes redirect) {
        try {
            LocalTime dayTimeEndOfWork = LocalTime.of(16,0);
            LocalTime dayTimeStartOfWork = LocalTime.of(8,0);
            LocalDateTime expectedEndDate;
            Task task = taskRepository.findById(taskDto.getId()).get();
            task.setTaskName(taskDto.getTaskName());
            task.setDescription(taskDto.getDescription());
            task.setDuration(taskDto.getDuration());
            if(taskDto.getStartDate() != null) {
                task.setStartDate(taskDto.getStartDate());
            }

            if (task.getDuration() <= 8) {
                if (LocalTime.of(task.getStartDate().plusHours((long) task.getDuration()).getHour(),
                        task.getStartDate().plusHours((long) task.getDuration()).getMinute()).isAfter(dayTimeEndOfWork)) {
                    expectedEndDate = LocalDateTime.of(task.getStartDate().getYear(),
                            task.getStartDate().getMonth(),
                            task.getStartDate().getDayOfMonth() + 1,
                            8 + task.getStartDate().plusHours((long) task.getDuration()).getHour() - 16,
                            task.getStartDate().plusHours((long) task.getDuration()).getMinute());

                    task.setEndDate(expectedEndDate);
                } else {
                    task.setEndDate(task.getStartDate().plusHours((long) task.getDuration()));
                }
            } else {
                int daysToRender = (int) task.getDuration() / 8;
                int hoursInEndOfDate = (int) task.getDuration() - (daysToRender * 8);
                expectedEndDate = task.getStartDate().plusDays(daysToRender);
                if (LocalTime.of(expectedEndDate.plusHours(hoursInEndOfDate).getHour(), expectedEndDate.getMinute()).isAfter(dayTimeEndOfWork)) {
                    //dayTimeStartOfWork.plusHours(16 - expectedEndDate.getHour());
                    dayTimeStartOfWork.plusMinutes(expectedEndDate.getMinute());
                    expectedEndDate = LocalDateTime.of(task.getStartDate().getYear(),
                            task.getStartDate().getMonth(),
                            task.getStartDate().getDayOfMonth() + daysToRender + 1,
                            8 + expectedEndDate.plusHours(hoursInEndOfDate).getHour() - 16,
                            expectedEndDate.getMinute());
                    task.setEndDate(expectedEndDate);
                } else {
                    task.setEndDate(expectedEndDate.plusHours(hoursInEndOfDate));
                }
            }


            updateAllDependencies(task);
            taskRepository.save(task);
            redirect.addFlashAttribute("editStatus","success");
            model.addAttribute("task", task);

        } catch (Exception e) {
            redirect.addFlashAttribute("editStatus","failed");
        }
    }
    public void updateAllDependencies(Task taskDependenciesToBeUpdate) {
        LocalTime dayTimeEndOfWork = LocalTime.of(16,0);
        LocalTime dayTimeStartOfWork = LocalTime.of(8,0);
        LocalDateTime expectedEndDate;

        List<Task> tasks = taskRepository.findAll();
        List<Task> dependents = new ArrayList<>();
        for(Task task: tasks) {
            for( Task dependent: task.getDependencies()) {
                if(dependent.getId().equals(taskDependenciesToBeUpdate.getId())) {
                    dependents.add(task);
                }
            }
        }
        Task taskWithHighestEndTime = null;
        if(!dependents.isEmpty()){
            for(Task dependency: dependents) {
                Task dependent = taskRepository.findById(dependency.getId()).get();

                for(Task dependentsDependency: dependent.getDependencies()) {
                    if(taskWithHighestEndTime == null) {
                        taskWithHighestEndTime = dependentsDependency;
                    } else {
                        if(taskWithHighestEndTime.getEndDate().isAfter(dependentsDependency.getEndDate())) {
                            taskWithHighestEndTime= dependentsDependency;
                        }
                    }
                }
                dependent.setStartDate(taskWithHighestEndTime.getEndDate());
                if (dependent.getDuration() <= 8) {

                    if (LocalTime.of(dependent.getStartDate().plusHours((long) dependent.getDuration()).getHour(),
                            dependent.getStartDate().plusHours((long) dependent.getDuration()).getMinute()).isAfter(dayTimeEndOfWork)) {

                        expectedEndDate = LocalDateTime.of(dependent.getStartDate().getYear(),
                                dependent.getStartDate().getMonth(),
                                dependent.getStartDate().getDayOfMonth() + 1,
                                8 + dependent.getStartDate().plusHours((long) dependent.getDuration()).getHour() - 16,
                                dependent.getStartDate().plusHours((long) dependent.getDuration()).getMinute());

                        dependent.setEndDate(expectedEndDate);
                    } else {
                        dependent.setEndDate(dependent.getStartDate().plusHours((long)dependent.getDuration()));
                    }
                } else {
                    int daysToRender = (int) dependent.getDuration() / 8;
                    int hoursInEndOfDate = (int) dependent.getDuration() - (daysToRender * 8);
                    expectedEndDate = dependent.getStartDate().plusDays(daysToRender);
                    if (LocalTime.of(expectedEndDate.plusHours(hoursInEndOfDate).getHour(), expectedEndDate.getMinute()).isAfter(dayTimeEndOfWork)) {

                        dayTimeStartOfWork.plusMinutes(expectedEndDate.getMinute());
                        expectedEndDate = LocalDateTime.of(dependent.getStartDate().getYear(),
                                dependent.getStartDate().getMonth(),
                                dependent.getStartDate().getDayOfMonth() + daysToRender + 1,
                                8 + expectedEndDate.plusHours(hoursInEndOfDate).getHour() - 16,
                                expectedEndDate.getMinute());
                        dependent.setEndDate(expectedEndDate);
                    } else {
                        dependent.setEndDate(expectedEndDate.plusHours(hoursInEndOfDate));
                    }
                }
                taskRepository.save(dependent);
                updateAllDependencies(dependent);
            }
        }
    }
    @Override
    public String deleteTask(Model model, Long taskId, RedirectAttributes redirect) {
        Task task = taskRepository.findById(taskId).get();
        try {
            taskRepository.delete(task);
            redirect.addFlashAttribute("deletedTask","success");
        } catch (Exception e) {
            redirect.addFlashAttribute("deletedTask","failed");
        }
        return "redirect:/project/info/" + task.getProject().getId();
    }

    @Override
    public void getTaskDetails(Model model, Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        model.addAttribute("task",task);
    }

    @Override
    public void checkTaskIfHasDependencies(Model model, Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        if(task.getDependencies().isEmpty()) {
            model.addAttribute("noDependencies", true);
        } else {
            model.addAttribute("noDependencies", false);
        }
    }

    @Override
    public void saveDependencies(Model model, TaskDependencyDto taskDependencyDto, RedirectAttributes redirect) {
        Task task = taskRepository.findById(taskDependencyDto.getId()).get();

            for(Long dependency: taskDependencyDto.getDependencies()) {
                task.getDependencies().add(taskRepository.findById(dependency).get());
            }
            taskRepository.save(task);
        try {
            for(Long dependency: taskDependencyDto.getDependencies()) {
                updateAllDependencies(taskRepository.findById(dependency).get());
            }
        } catch (Exception e) {}
    }

    @Override
    public void deleteDependency(Model model, Long taskId, Long dependencyId, RedirectAttributes redirect) {
        try {
            Task task = taskRepository.findById(taskId).get();
            Task dependency = taskRepository.findById(dependencyId).get();

            List<Task> dependencies = task.getDependencies();
            dependencies.remove(dependency);
            task.setDependencies(dependencies);
            taskRepository.save(task);

            for(Task taskDependency: dependencies) {
                updateAllDependencies(taskRepository.findById(taskDependency.getId()).get());
            }
            redirect.addFlashAttribute("deletedTask","success");
        } catch (Exception e) {
            redirect.addFlashAttribute("deletedTask","failed");

        }
    }
}
