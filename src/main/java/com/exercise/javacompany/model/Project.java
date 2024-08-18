package com.exercise.javacompany.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor //Wird für JPA benötigt
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    /*
    var name: String,
    var description: String,
    var priority: Int, //1 = highest priority, 2,3 ...
    @ManyToOne //Hauptverantworlicher
    var responsibleEmployee: Employee
) {
        //Beziehung "bearbeitet"
        @ManyToMany
        var assignedEmployees: MutableList<Employee> = mutableListOf()
*/


}



