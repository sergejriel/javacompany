package com.exercise.javacompany.controller;

import com.exercise.javacompany.DTO.ProjectDTOs.ProjectDTO;
import com.exercise.javacompany.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {


    /*
    Das nennt sich Constructor Injection.
    eine Field Injection wäre das hier:
    @Autowired
    private ProjectService projectService;

    Einfacher zu lesen, ist aber schlechter für automatische Tests.
    */
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping("/projects")
    public List<ProjectDTO> getProjects() {
        return projectService.getProjects();
    }

}
