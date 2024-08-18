package com.exercise.javacompany.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @OneToMany(mappedBy = "responsibleEmployee")
    private List<Project> responsibleProjects = new ArrayList<>();

    @ManyToMany(mappedBy = "assignedEmployees")
    private List<Project> assignedProjects = new ArrayList<>();

    @OneToMany(mappedBy = "rankingEmployee")
    private List<Ranking> ownRankings = new ArrayList<>();

    @OneToOne(mappedBy = "employee")
    private Workplace workplace;


    public Employee(String name, EmployeeStatus status) {
        this.name = name;
        this.status = status;
    }
}

