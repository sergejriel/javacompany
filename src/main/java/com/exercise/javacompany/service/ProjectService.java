package com.exercise.javacompany.service;

import com.exercise.javacompany.DTO.EmployeeDTOs.EmployeeDTO;
import com.exercise.javacompany.DTO.ProjectDTOs.*;
import com.exercise.javacompany.model.Employee;
import com.exercise.javacompany.model.Project;
import com.exercise.javacompany.model.log.ProjectResponsibleEmployeeChangeLog;
import com.exercise.javacompany.repository.EmployeeRepository;
import com.exercise.javacompany.repository.ProjectRepository;
import com.exercise.javacompany.repository.log.ProjectResponsibleEmployeeChangeLogRepository;
import com.exercise.javacompany.service.deep.DeepServiceGate;
import com.exercise.javacompany.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectResponsibleEmployeeChangeLogRepository projectResponsibleEmployeeChangeLogRepository;
    private final DeepServiceGate deepServiceGate;

    @Autowired
    public ProjectService(
            ProjectRepository projectRepository,
            EmployeeRepository employeeRepository,
            ProjectResponsibleEmployeeChangeLogRepository projectResponsibleEmployeeChangeLogRepository,
            DeepServiceGate deepServiceGate
    ) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.projectResponsibleEmployeeChangeLogRepository = projectResponsibleEmployeeChangeLogRepository;
        this.deepServiceGate = deepServiceGate;
    }

    public ProjectDTO getProject(Long projectId) {

        return projectRepository.findById(projectId)
                .map(ProjectDTO::new)
                .orElseThrow(() -> new IllegalStateException("project with id " + projectId + " does not exists"));
    }

    public List<ProjectDTO> getProjects() {

       return projectRepository.findAll()
               .stream()
               .sorted(Comparator.comparingInt(Project::getPriority))
               .map(ProjectDTO::new)
               .toList();
    }

    public List<ProjectNameDTO> getAllProjectNames() {
        return projectRepository
                .findAll()
                .stream()
                .map(ProjectNameDTO::new)
                .toList();
    }

    @Transactional
    public List<EmployeeDTO> getAllAssignedEmployeesToProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalStateException("project with id " + projectId + " does not exists"))
                .getAssignedEmployees()
                .stream()
                .map(EmployeeDTO::new)
                .toList();
    }

    public List<ProjectPriorityDisplayDTO> getAllProjectPriority() {
        return projectRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Project::getPriority))
                .map(ProjectPriorityDisplayDTO::new)
                .toList();
    }

    public void createProject(ProjectCreateDTO data) {
        Employee responsibleEmployee = employeeRepository.findById(data.getResponsibleEmployeeId())
                .orElseThrow(() -> new IllegalStateException("responsibleEmployee with id " + data.getResponsibleEmployeeId() + " does not exists"));

        projectRepository.save(
                new Project(
                        data.getName(),
                        data.getDescription(),
                        data.getPriority(),
                        responsibleEmployee
                )
        );
        //TODO SR: Bau hier ein Log ein.
    }

    public void assignEmployeesToProject(Long projectId ,List<Long> assignedEmployeesIds) { //TODO SR: Prüfe, ob man einfach so die Long Liste übergeben kann.
        if(assignedEmployeesIds.isEmpty()) return;

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalStateException("project with id " + projectId + " does not exists"));

        project.getAssignedEmployees().clear();
        project.setAssignedEmployees(employeeRepository.findAllById(assignedEmployeesIds)); //TODO SR: Teste ob das so funktioniert.
    }

    //TODO SR: Schau dir dazu die Funktion von Workplace an.

    @Transactional
    public void updateProject(Long projectId, ProjectCreateDTO data) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalStateException("project with id " + projectId + " does not exists"));

        if(ValidationUtils.checkFieldIsUpdatedAndValid(data.getName(),project.getName())) {
            project.setName(data.getName());
        }

        if(ValidationUtils.checkFieldIsUpdatedAndValid(data.getDescription(),project.getDescription())) {
            project.setName(data.getDescription());
        }

        if(data.getPriority() != project.getPriority()) {
            project.setPriority(data.getPriority());
        }

        if(!Objects.equals(data.getResponsibleEmployeeId(), project.getResponsibleEmployee().getId())) {
            Employee responsibleEmployee = employeeRepository.findById(data.getResponsibleEmployeeId())
                    .orElseThrow(() -> new IllegalStateException("employee with id " + data.getResponsibleEmployeeId() + " does not exists"));
            project.setResponsibleEmployee(responsibleEmployee);
        }
    }

    public void swapProjectPriority(List<ProjectPriorityDTO> data) {
        Project project = null;

        for (ProjectPriorityDTO priorityEntry : data) {
            project = projectRepository.findById(priorityEntry.getId())
                    .orElseThrow(() -> new IllegalStateException("project with id " + priorityEntry.getId() + " does not exists"));

            project.setPriority(priorityEntry.getPriority());

            projectRepository.save(project); //bei vielen Datensätzen könnte man über batching nachdenken.
        }
    }

    //TODO SR: Hier werden weniger Daten durch die API gesendet. Teste es und ersetzte damit die erste Variante.
    public void swapProjectPriorityV2(int oldPosition, int newPosition) {
        List<Project> projects = new java.util.ArrayList<>(projectRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Project::getPriority))
                .toList());

        if (oldPosition < 0 || oldPosition >= projects.size() || newPosition < 0 || newPosition >= projects.size()) {
            throw new IllegalArgumentException("Position(s) are out of bounds.");
        }

        Project projectToMove = projects.get(oldPosition);

        projects.remove(oldPosition);

        projects.add(newPosition, projectToMove);

        for (int i = 0; i < projects.size(); i++) {
            projects.get(i).setPriority(i+1);
        }

        projectRepository.saveAll(projects);
    }

    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    public void createResponsibleEmployeeChangeLog(Long responsibleEmployeeId, Project responsibleProject) {
        Employee responsibleEmployee = employeeRepository.findById(responsibleEmployeeId).orElseThrow();

        ProjectResponsibleEmployeeChangeLog log = projectResponsibleEmployeeChangeLogRepository.save(
                new ProjectResponsibleEmployeeChangeLog(
                        responsibleEmployee,
                        responsibleProject
                )
        );

        deepServiceGate.handle(log);
    }

    public List<ProjectResponsibleEmployeeChangeLogDTO> getResponsibleEmployeeChangeLogBetween(
            Long projectId,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        List<ProjectResponsibleEmployeeChangeLog> allChangeLogs = projectResponsibleEmployeeChangeLogRepository.findAllByResponsibleProjectIdAndOccurredBetween(
                projectId,
                startTime,
                endTime.toLocalDate().atTime(23,59,59)
        );
        return allChangeLogs.stream().map(ProjectResponsibleEmployeeChangeLogDTO::new).toList();
    }

    public List<ProjectResponsibleEmployeeChangeLogDTO> getAllResponsibleEmployeeChangeLog() {
        List<ProjectResponsibleEmployeeChangeLog> allChangeLogs = projectResponsibleEmployeeChangeLogRepository.findAll();
        return allChangeLogs.stream().map(ProjectResponsibleEmployeeChangeLogDTO::new).toList();
    }
}
