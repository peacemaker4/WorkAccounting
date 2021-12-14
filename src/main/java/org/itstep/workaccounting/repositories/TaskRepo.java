package org.itstep.workaccounting.repositories;

import org.itstep.workaccounting.entities.DbUser;
import org.itstep.workaccounting.entities.Task;
import org.itstep.workaccounting.entities.WorkProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long > {
    List<Task> getAllByUser(DbUser user);

    List<Task> getAllByProject(WorkProject project);

    Task findTaskById(Long id);

    List<Task> findAllByUserAndNameContainingIgnoreCase(DbUser user, String keyword);

}
