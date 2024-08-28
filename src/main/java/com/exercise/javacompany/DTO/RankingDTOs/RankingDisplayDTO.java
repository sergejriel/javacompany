package com.exercise.javacompany.DTO.RankingDTOs;

import com.exercise.javacompany.DTO.EmployeeDTOs.EmployeeNameDTO;
import com.exercise.javacompany.model.Ranking;

public class RankingDisplayDTO {
    private EmployeeNameDTO rankedPerson;
    private Long rankValueSum;

    public RankingDisplayDTO(Ranking model, Long rankValueSum) {
        this.rankedPerson = new EmployeeNameDTO(model.getRankedPerson());
        this.rankValueSum = rankValueSum;
    }
}
