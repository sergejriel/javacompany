package com.exercise.javacompany.model.log;

import com.exercise.javacompany.model.Employee;
import com.exercise.javacompany.model.Project;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ProjectResponsibleEmployeeChangeLog extends LogEntry {
    @ManyToOne
    Employee responsibleEmployee;

    @ManyToOne
    Project responsibleProject;

    public ProjectResponsibleEmployeeChangeLog(Employee responsibleEmployee, Project responsibleProject) {
        this.responsibleEmployee = responsibleEmployee;
        this.responsibleProject = responsibleProject;
    }
}
