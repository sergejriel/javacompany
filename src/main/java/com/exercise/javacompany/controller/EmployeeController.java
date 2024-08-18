package com.exercise.javacompany.controller;

import com.exercise.javacompany.DTO.EmployeeCreateDTO;
import com.exercise.javacompany.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController( EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    public void createEmployee(
            @RequestBody EmployeeCreateDTO employeeCreateDTO
    ) {
        employeeService.createEmployee(employeeCreateDTO);
    }
}
