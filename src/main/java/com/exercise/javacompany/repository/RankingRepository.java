package com.exercise.javacompany.repository;

import com.exercise.javacompany.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    Optional<Ranking> findByRankingEmployee_IdAndRankedPerson_Id(Long rankingEmployee, Long rankedPerson);

    //TODO implementiere den restlichen kotlin Code:
    /*
        @Query(
        """select new com.uniwunder.template.data.RankingDisplayData(r.rankedPerson.name, sum(r.rankValue))
                from Ranking r group by 1 order by 2 desc"""
    )
    fun getAllRankedPersonGroupByIdOrderBySumRankValue(): List<RankingDisplayData>
    @Query(
        """select new com.uniwunder.template.data.RankingWorstRankedPersonDisplayData( r2.rankedPerson.id, r2.rankValue)
        from Ranking r2
        where r2.rankingEmployee.id = :rankingEmployeeId
        AND r2.rankValue = (select min(r.rankValue)
                            from Ranking r
                            where r.rankingEmployee.id = :rankingEmployeeId
                            group by r.rankingEmployee.id)"""
    )
    fun getWorstRankedPersonsOfRankingEmployeeById(@Param("rankingEmployeeId") rankingEmployeeId: Long): List<RankingWorstRankedPersonDisplayData>
     */

}
