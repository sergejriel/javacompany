package com.exercise.javacompany.repository;

import com.exercise.javacompany.DTO.RankingDTOs.RankingDisplayDTO;
import com.exercise.javacompany.DTO.RankingDTOs.RankingWorstRankedPersonDisplayDTO;
import com.exercise.javacompany.model.Employee;
import com.exercise.javacompany.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    Optional<Ranking> findByRankingEmployee_IdAndRankedPerson_Id(Long rankingEmployee, Long rankedPerson);

    List<Ranking> findAllByRankingEmployeeOrRankedPerson(Employee rankingEmployee, Employee rankedPerson);

    //TODO bringe folgende Funktionen zum Laufen:

        @Query("select new com.exercise.javacompany.DTO.RankingDTOs.RankingDisplayDTO(r.rankedPerson.name, avg(r.rankValue))" +
                "from Ranking r "+
                "GROUP BY 1 " +
                "ORDER BY 2 DESC")
        List<RankingDisplayDTO> getAllRankedPersonGroupByIdOrderByAvgRankValue();

    @Query("select new com.exercise.javacompany.DTO.RankingDTOs.RankingWorstRankedPersonDisplayDTO( r2.rankedPerson.id, r2.rankValue) from Ranking r2 " +
            "where r2.rankingEmployee.id = :rankingEmployeeId " +
            "AND r2.rankValue = (select min(r.rankValue) " +
                                "from Ranking r " +
                                "where r.rankingEmployee.id = :rankingEmployeeId " +
                                "group by r.rankingEmployee.id) ")
    List<RankingWorstRankedPersonDisplayDTO> getWorstRankedPersonsOfRankingEmployeeById(@Param("rankingEmployeeId") Long rankingEmployeeId);

}
