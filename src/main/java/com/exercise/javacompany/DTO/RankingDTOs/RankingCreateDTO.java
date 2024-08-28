package com.exercise.javacompany.DTO.RankingDTOs;

import com.exercise.javacompany.DTO.EmployeeDTOs.EmployeeNameDTO;
import com.exercise.javacompany.model.Ranking;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RankingCreateDTO {
    private EmployeeNameDTO rankingEmployee;
    private EmployeeNameDTO rankedPerson;
    private int rankValue;

    public RankingCreateDTO(Ranking model) {
        this.rankingEmployee = new EmployeeNameDTO(model.getRankingEmployee());
        this.rankedPerson = new EmployeeNameDTO(model.getRankedPerson());
        this.rankValue = model.getRankValue();
    }
}