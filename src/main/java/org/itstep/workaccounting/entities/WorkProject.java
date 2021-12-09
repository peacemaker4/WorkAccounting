package org.itstep.workaccounting.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class WorkProject extends BaseEntity {

    private String name;

    private String description;

    public WorkProject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Task> tasks;


}
