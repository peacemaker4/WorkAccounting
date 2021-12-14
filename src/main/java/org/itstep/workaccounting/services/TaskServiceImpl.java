package org.itstep.workaccounting.services;

import org.itstep.workaccounting.entities.DbUser;
import org.itstep.workaccounting.entities.Task;
import org.itstep.workaccounting.entities.TaskDescription;
import org.itstep.workaccounting.entities.WorkProject;
import org.itstep.workaccounting.repositories.TaskDescriptionRepo;
import org.itstep.workaccounting.repositories.TaskRepo;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@EnableWebSecurity
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepository;
    private final TaskDescriptionRepo taskDescriptionRepository;

    public TaskServiceImpl(TaskRepo taskRepository, TaskDescriptionRepo taskDescriptionRepository) {
        this.taskRepository = taskRepository;
        this.taskDescriptionRepository = taskDescriptionRepository;
    }

    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public TaskDescription addTaskDescription(TaskDescription taskDescription) {
        return taskDescriptionRepository.save(taskDescription);
    }

    @Override
    public List<Task> getAllTasksOfUser(DbUser user) {
        return taskRepository.getAllByUser(user);
    }

    @Override
    public List<Task> getAllTasksOfProject(WorkProject project) {
        return taskRepository.getAllByProject(project);
    }

    @Override
    public List<Task> findTasks(DbUser user, String keyword) {
        if (keyword != null) {
            return taskRepository.findAllByUserAndNameContainingIgnoreCase(user, keyword);
        }
        return taskRepository.getAllByUser(user);
    }


    @Override
    public Task getTask(Long id) {
        return taskRepository.findTaskById(id);
    }

    @Override
    public Long daysBeforeDeadline(Task task) {
        LocalDate deadline = task.getDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.DAYS.between(LocalDate.now(), deadline);
    }
}
