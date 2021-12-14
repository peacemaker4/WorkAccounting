package org.itstep.workaccounting.services;

import org.hibernate.jdbc.Work;
import org.itstep.workaccounting.entities.DbUser;
import org.itstep.workaccounting.entities.Task;
import org.itstep.workaccounting.entities.TaskDescription;
import org.itstep.workaccounting.entities.WorkProject;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    Task addTask(Task task);

    TaskDescription addTaskDescription(TaskDescription taskDescription);

    List<Task> getAllTasksOfUser(DbUser user);

    List<Task> getAllTasksOfProject(WorkProject project);

    List<Task> findTasks(DbUser user, String keyword);


    Task getTask(Long id);

    Long daysBeforeDeadline(Task task);

}
