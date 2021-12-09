package org.itstep.workaccounting.services;

import org.itstep.workaccounting.entities.Task;
import org.itstep.workaccounting.repositories.TaskRepo;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;

@Service
@EnableWebSecurity
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepository;

    public TaskServiceImpl(TaskRepo taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }
}
