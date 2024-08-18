package com.exercise.javacompany.service;

import com.exercise.javacompany.DTO.EmployeeCreateDTO;
import com.exercise.javacompany.model.Employee;
import com.exercise.javacompany.repository.EmployeeRepository;
import com.exercise.javacompany.repository.WorkplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(
            WorkplaceRepository workplaceRepository,
            EmployeeRepository employeeRepository
    ) {
        this.employeeRepository = employeeRepository;
    }

    public void createEmployee(EmployeeCreateDTO employeeCreateDTO) {
        employeeRepository.save(new Employee(
                employeeCreateDTO.getName(),
                employeeCreateDTO.getStatus()
        ));
    }

    //TODO SR: Lege hier weitere Employees an.
}
