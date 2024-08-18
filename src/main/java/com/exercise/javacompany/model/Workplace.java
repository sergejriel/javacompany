package com.exercise.javacompany.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Workplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private int monitorCount;

    @OneToOne
    private Employee employee;

    public Workplace(String description, int monitorCount, Employee employee) {
        this.description = description;
        this.monitorCount = monitorCount;
        this.employee = employee;
    }
}
