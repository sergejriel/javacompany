package com.exercise.javacompany.repository.log;

import com.exercise.javacompany.model.log.ProjectResponsibleEmployeeChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProjectResponsibleEmployeeChangeLogRepository extends JpaRepository<ProjectResponsibleEmployeeChangeLog, Long> {

    List<ProjectResponsibleEmployeeChangeLog> findAllByResponsibleProjectIdAndOccurredBetween(
            Long id,
            LocalDateTime occurredTimeStart,
            LocalDateTime occurredTimeEnd
    );
}
