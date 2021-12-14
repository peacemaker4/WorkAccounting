package org.itstep.workaccounting.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.itstep.workaccounting.entities.Task;

@Data
@AllArgsConstructor
public class ProjectTaskModel {
    private Long project_id;

    private String project_name;

    private Task task;
}
