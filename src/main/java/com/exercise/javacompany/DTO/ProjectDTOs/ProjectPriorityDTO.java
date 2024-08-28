package com.exercise.javacompany.DTO.ProjectDTOs;

import com.exercise.javacompany.model.Project;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProjectPriorityDTO {
    Long id;
    int priority;

    public ProjectPriorityDTO(Project model) {
        this.id = model.getId();
        this.priority = model.getPriority();
    }
}