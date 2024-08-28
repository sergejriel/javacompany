package com.exercise.javacompany.DTO.WorkplaceDTOs;

import com.exercise.javacompany.model.Workplace;

public class WorkplaceNameDTO {
    private Long id;
    private String description;

    public WorkplaceNameDTO(Workplace model) {
        this.id = model.getId();
        this.description = model.getDescription();
    }
}
