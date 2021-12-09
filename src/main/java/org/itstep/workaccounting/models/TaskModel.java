package org.itstep.workaccounting.models;

import lombok.Data;

@Data
public class TaskModel {
    private String name;

    private String description;

    private Long user_id;

    private Long project_id;
}
