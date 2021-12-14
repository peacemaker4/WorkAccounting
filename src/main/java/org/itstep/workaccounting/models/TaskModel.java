package org.itstep.workaccounting.models;

import lombok.Data;

import java.util.Date;

@Data
public class TaskModel {
    private String name;

    private String description;

    private String deadline;

    private Long user_id;

    private Long project_id;
}
