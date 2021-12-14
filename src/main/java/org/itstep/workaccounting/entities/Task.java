package org.itstep.workaccounting.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Task extends BaseEntity {

    private String name;

    private String description;

    private String status;

    private Date deadline;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_project_id")
    private WorkProject project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private DbUser user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_description_id")
    private List<TaskDescription> taskDescriptions;

    public TaskDescription getTaskDescription(LocalDate date){
        for(var t : taskDescriptions){
            if(date.equals(t.getDate())){
                return t;
            }
        }
        return null;
    }

    public Integer getTaskTime(LocalDate date){
        Integer hours = 0;
        if(taskDescriptions != null){
            for(var t : taskDescriptions){
                if(t.getDate().isBefore(date) || t.getDate().isEqual(date))
                    hours += (t.getTime_to().getHour() - t.getTime_from().getHour());
            }
        }
        return hours;
    }

    public Task(String name, String description, String status, Date deadline, WorkProject project, DbUser user) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.project = project;
        this.user = user;
    }
}
