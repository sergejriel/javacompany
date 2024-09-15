package com.exercise.javacompany.DTO.RankingDTOs;

import com.exercise.javacompany.DTO.EmployeeDTOs.EmployeeNameDTO;
import com.exercise.javacompany.model.Ranking;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RankingDisplayDTO {
    //private EmployeeNameDTO rankedPerson;
    private String rankedPersonName;
    private Double rankValueAvg;


/*    public RankingDisplayDTO(Ranking model, Double rankValueAvg) {
        this.rankedPerson = new EmployeeNameDTO(model.getRankedPerson());
        this.rankValueAvg = rankValueAvg;
    }*/

    public RankingDisplayDTO(String rankedPersonName, Double rankValueAvg) {
        this.rankedPersonName = rankedPersonName;
        this.rankValueAvg = rankValueAvg;
    }
}
