package com.exercise.javacompany.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity
@Data
@NoArgsConstructor
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee rankingEmployee;

    @ManyToOne
    private Employee rankedPerson;

    private int rankValue;

    public Ranking(Employee rankingEmployee, Employee rankedPerson, int rankValue) {
        this.rankingEmployee = rankingEmployee;
        this.rankedPerson = rankedPerson;
        this.rankValue = rankValue;
    }
}
