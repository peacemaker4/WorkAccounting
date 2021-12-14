package org.itstep.workaccounting.services;

import org.hibernate.jdbc.Work;
import org.itstep.workaccounting.entities.DbUser;
import org.itstep.workaccounting.entities.Task;
import org.itstep.workaccounting.entities.WorkProject;
import org.itstep.workaccounting.repositories.WorkProjectRepo;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableWebSecurity
public class ProjectServiceImpl implements ProjectService {

    private final WorkProjectRepo workProjectRepository;

    public ProjectServiceImpl(WorkProjectRepo workProjectRepository) {
        this.workProjectRepository = workProjectRepository;
    }

    @Override
    public WorkProject addProject(WorkProject workProject) {
        return workProjectRepository.save(workProject);
    }

    @Override
    public List<WorkProject> getProjects() {
        return workProjectRepository.findAll();
    }

    @Override
    public WorkProject getProject(Long id) {
        return workProjectRepository.findWorkProjectById(id);
    }

}
