package com.exercise.javacompany.DTO.EmployeeDTOs;

import com.exercise.javacompany.DTO.ProjectDTOs.ProjectDTO;
import com.exercise.javacompany.DTO.ProjectDTOs.ProjectNameDTO;
import com.exercise.javacompany.DTO.WorkplaceDTOs.WorkplaceDTO;
import com.exercise.javacompany.DTO.WorkplaceDTOs.WorkplaceNameDTO;
import com.exercise.javacompany.model.Employee;
import com.exercise.javacompany.model.EmployeeStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String name;
    private EmployeeStatus status;
    private List<ProjectNameDTO> responsibleProjects;
    private List<ProjectNameDTO> assignedProjects;
    private WorkplaceDTO workplace;

    public EmployeeDTO(Employee model) {
        this.id = model.getId();
        this.name = model.getName();
        this.status = model.getStatus();
        this.responsibleProjects = model.getResponsibleProjects().stream().map(ProjectNameDTO::new).collect(Collectors.toList());
        this.assignedProjects = model.getAssignedProjects().stream().map(ProjectNameDTO::new).collect(Collectors.toList());
        this.workplace = new WorkplaceDTO(model.getWorkplace());
    }
}
