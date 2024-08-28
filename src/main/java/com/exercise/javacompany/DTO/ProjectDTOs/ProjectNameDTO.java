package com.exercise.javacompany.DTO.ProjectDTOs;

import com.exercise.javacompany.model.Project;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectNameDTO {
    Long id;
    String name;

    public ProjectNameDTO(Project model) {
        this.id = model.getId();
        this.name = model.getName();
    }
}
