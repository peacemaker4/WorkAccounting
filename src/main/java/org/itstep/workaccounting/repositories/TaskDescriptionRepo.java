package org.itstep.workaccounting.repositories;

import org.itstep.workaccounting.entities.DbUser;
import org.itstep.workaccounting.entities.Task;
import org.itstep.workaccounting.entities.TaskDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskDescriptionRepo extends JpaRepository<TaskDescription, Long > {
}
