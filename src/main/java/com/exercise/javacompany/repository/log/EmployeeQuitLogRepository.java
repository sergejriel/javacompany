package com.exercise.javacompany.repository.log;

import com.exercise.javacompany.model.log.EmployeeQuitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeQuitLogRepository extends JpaRepository<EmployeeQuitLog, Long> {
}
