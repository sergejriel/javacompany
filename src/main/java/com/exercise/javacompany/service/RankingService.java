package com.exercise.javacompany.service;

import com.exercise.javacompany.DTO.RankingDTOs.RankingCreateDTO;
import com.exercise.javacompany.DTO.RankingDTOs.RankingDTO;
import com.exercise.javacompany.DTO.RankingDTOs.RankingDisplayDTO;
import com.exercise.javacompany.DTO.RankingDTOs.RankingWorstRankedPersonDisplayDTO;
import com.exercise.javacompany.model.Ranking;
import com.exercise.javacompany.repository.EmployeeRepository;
import com.exercise.javacompany.repository.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingService {

    private final RankingRepository rankingRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public RankingService(
            EmployeeRepository employeeRepository,
            RankingRepository rankingRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.rankingRepository = rankingRepository;
    }

    public RankingDTO getRanking(Long rankingId) {
        return rankingRepository.findById(rankingId).map(RankingDTO::new).orElseThrow();
    }

    public List<RankingDTO> getAllRankings() {
        return rankingRepository.findAll().stream().map(RankingDTO::new).toList();
    }

    public List<RankingDisplayDTO> getAllRankedPersonGroupByIdOrderByAvgRankValue(){
        return  rankingRepository.getAllRankedPersonGroupByIdOrderByAvgRankValue();
    }

    public List<RankingWorstRankedPersonDisplayDTO> getWorstRankedPersonOfRankingEmployeeById(Long rankingEmployeeId) {
        return rankingRepository.getWorstRankedPersonsOfRankingEmployeeById(rankingEmployeeId);
    }

    public void createRanking(RankingCreateDTO data) {
        if(data.getRankValue() < 1 || data.getRankValue() > 10) {
            throw new IllegalArgumentException("Rank value must be between 1 and 10.");
        }

        rankingRepository.save(new Ranking(
                employeeRepository.findById(data.getRankingEmployee().getId()).orElseThrow(),
                employeeRepository.findById(data.getRankedPerson().getId()).orElseThrow(),
                data.getRankValue()
        ));
    }

    public void updateRanking(Long rankingId, int newRankingValue) {
        if(newRankingValue < 1 || newRankingValue > 10) {
            throw new IllegalArgumentException("Rank value must be between 1 and 10.");
        }

        Ranking ranking = rankingRepository.findById(rankingId).orElseThrow();

        if(ranking.getRankValue() != newRankingValue) {
            ranking.setRankValue(newRankingValue);
            rankingRepository.save(ranking);
        }
    }

    public void deleteRanking(Long rankingId) {
        rankingRepository.deleteById(rankingId);
    }
}
