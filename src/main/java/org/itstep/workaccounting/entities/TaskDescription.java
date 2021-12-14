package org.itstep.workaccounting.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TaskDescription extends BaseEntity {

    private LocalTime  time_from;

    private LocalTime time_to;

    private String jobType;

    private String comment;

    private LocalDate date;

}
