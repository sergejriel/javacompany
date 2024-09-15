package com.exercise.javacompany.DTO.ProjectDTOs;

import com.exercise.javacompany.DTO.EmployeeDTOs.EmployeeNameDTO;
import com.exercise.javacompany.model.log.ProjectResponsibleEmployeeChangeLog;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProjectResponsibleEmployeeChangeLogDTO {
    Long id;
    LocalDateTime occurred;
    EmployeeNameDTO responsibleEmployee;
    ProjectNameDTO responsibleProject;

    public ProjectResponsibleEmployeeChangeLogDTO(ProjectResponsibleEmployeeChangeLog model) {
        this.id = model.getId();
        this.occurred = model.getOccurred();
        this.responsibleEmployee = new EmployeeNameDTO(model.getResponsibleEmployee());
        this.responsibleProject = new ProjectNameDTO(model.getResponsibleProject());
    }
}
