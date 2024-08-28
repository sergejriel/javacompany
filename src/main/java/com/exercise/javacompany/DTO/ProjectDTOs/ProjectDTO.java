package com.exercise.javacompany.DTO.ProjectDTOs;

import com.exercise.javacompany.DTO.EmployeeDTOs.EmployeeNameDTO;
import com.exercise.javacompany.model.Project;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private int priority;
    private EmployeeNameDTO responsibleEmployee;

    public ProjectDTO(@NotNull Project model) {
        this.id = model.getId();
        this.name = model.getName();
        this.description = model.getDescription();
        this.priority = model.getPriority();
        this.responsibleEmployee = new EmployeeNameDTO(model.getResponsibleEmployee());
    }
}
