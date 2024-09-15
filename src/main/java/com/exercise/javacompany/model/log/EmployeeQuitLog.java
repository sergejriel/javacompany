package com.exercise.javacompany.model.log;

import com.exercise.javacompany.model.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class EmployeeQuitLog extends LogEntry {
    @ManyToOne
    Employee quitEmployee;

    public EmployeeQuitLog(Employee quitEmployee) {
        this.quitEmployee = quitEmployee;
    }
}
