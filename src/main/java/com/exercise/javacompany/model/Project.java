package com.exercise.javacompany.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor //Wird für JPA benötigt
public class Project extends BaseEntity {

    private String name;
    private String description;
    private int priority;

    @ManyToOne
    private Employee responsibleEmployee;

    @ManyToMany
    private List<Employee> assignedEmployees = new ArrayList<>();

    // Konstruktor, der die verantwortliche Anstellung übergibt
    public Project(String name, String description, int priority, Employee responsibleEmployee) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.responsibleEmployee = responsibleEmployee;
    }
}



