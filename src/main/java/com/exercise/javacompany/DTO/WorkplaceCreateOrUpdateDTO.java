package com.exercise.javacompany.DTO;

import com.exercise.javacompany.model.Employee;
import com.exercise.javacompany.model.Workplace;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Data
@NoArgsConstructor
public class WorkplaceCreateOrUpdateDTO {
    private String description;
    private int monitorCount;
    private Long employeeId;

    public WorkplaceCreateOrUpdateDTO(@NotNull Workplace model){
        this.description = model.getDescription();
        this.monitorCount = model.getMonitorCount();
        this.employeeId = Optional.ofNullable(model.getEmployee())
                .map(Employee::getId)
                .orElse(null); //Somit wird die Null-Sicherheit gewährleistet werden.

    }

}