package com.exercise.javacompany.DTO.EmployeeDTOs;

import com.exercise.javacompany.model.Employee;
import com.exercise.javacompany.model.EmployeeStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class EmployeeCreateDTO {
    private String name;
    private EmployeeStatus status;

    public EmployeeCreateDTO(@NotNull Employee model){
        this.name = model.getName();
        this.status = model.getStatus();
    }
}
