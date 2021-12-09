package org.itstep.workaccounting.repositories;

import org.itstep.workaccounting.entities.WorkProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkProjectRepo extends JpaRepository<WorkProject, Long > {
    WorkProject findWorkProjectById(Long id);
}
