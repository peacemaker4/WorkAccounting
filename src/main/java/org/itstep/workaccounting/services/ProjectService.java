package org.itstep.workaccounting.services;

import org.itstep.workaccounting.entities.DbUser;
import org.itstep.workaccounting.entities.WorkProject;

import java.util.List;

public interface ProjectService {
    WorkProject addProject(WorkProject workProject);

    List<WorkProject> getProjects();

    WorkProject getProject(Long id);
}
