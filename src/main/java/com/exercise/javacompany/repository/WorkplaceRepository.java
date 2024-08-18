package com.exercise.javacompany.repository;

import com.exercise.javacompany.model.Employee;
import com.exercise.javacompany.model.Workplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace, Long> {

    Optional<Workplace> findByEmployee(Employee employee);

}