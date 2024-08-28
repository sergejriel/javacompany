package com.exercise.javacompany.DTO.ProjectDTOs;

import com.exercise.javacompany.model.Project;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class ProjectCreateDTO {
    private String name;
    private String description;
    private int priority;
    private Long responsibleEmployeeId;

    public ProjectCreateDTO(@NotNull Project model) {
        this.name = model.getName();
        this.description = model.getDescription();
        this.priority = model.getPriority();
        this.responsibleEmployeeId = model.getResponsibleEmployee().getId();
    }
}