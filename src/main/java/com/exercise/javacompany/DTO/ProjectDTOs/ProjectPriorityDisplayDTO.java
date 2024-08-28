package com.exercise.javacompany.DTO.ProjectDTOs;

import com.exercise.javacompany.model.Project;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectPriorityDisplayDTO {
    Long id;
    String name;
    int priority;

    public ProjectPriorityDisplayDTO(Project model) {
        this.id = model.getId();
        this.name = model.getName();
        this.priority = model.getPriority();
    }
}