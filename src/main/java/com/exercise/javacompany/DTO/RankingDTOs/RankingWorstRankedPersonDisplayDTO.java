package com.exercise.javacompany.DTO.RankingDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RankingWorstRankedPersonDisplayDTO {
    private Long rankedPersonId;
    private int rankValue;

    public RankingWorstRankedPersonDisplayDTO(Long rankedPersonId, int rankValue) {
        this.rankedPersonId = rankedPersonId;
        this.rankValue = rankValue;
    }
}
