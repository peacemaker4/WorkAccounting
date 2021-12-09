package org.itstep.workaccounting.repositories;

import org.itstep.workaccounting.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long > {
}
