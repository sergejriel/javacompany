package com.exercise.javacompany.DTO.EmployeeDTOs;

import com.exercise.javacompany.model.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeNameDTO {
    private Long id;
    private String name;

    public EmployeeNameDTO(Employee model) {
        this.id = model.getId();
        this.name = model.getName();
    }
}
