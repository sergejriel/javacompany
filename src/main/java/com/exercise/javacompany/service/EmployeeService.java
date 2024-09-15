package com.exercise.javacompany.service;

import com.exercise.javacompany.DTO.EmployeeDTOs.EmployeeCreateDTO;
import com.exercise.javacompany.DTO.EmployeeDTOs.EmployeeDTO;
import com.exercise.javacompany.DTO.ProjectDTOs.ProjectDTO;
import com.exercise.javacompany.DTO.RankingDTOs.RankingDTO;
import com.exercise.javacompany.DTO.RankingDTOs.RankingWorstRankedPersonDisplayDTO;
import com.exercise.javacompany.model.Employee;
import com.exercise.javacompany.model.EmployeeStatus;
import com.exercise.javacompany.model.Ranking;
import com.exercise.javacompany.model.log.EmployeeQuitLog;
import com.exercise.javacompany.repository.EmployeeRepository;
import com.exercise.javacompany.repository.RankingRepository;
import com.exercise.javacompany.service.deep.DeepServiceGate;
import com.exercise.javacompany.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RankingRepository rankingRepository;
    private final WorkplaceService workplaceService;
    private final DeepServiceGate deepServiceGate;

    @Autowired
    public EmployeeService(
            EmployeeRepository employeeRepository,
            WorkplaceService workplaceService,
            RankingRepository rankingRepository,
            DeepServiceGate deepServiceGate
    ) {
        this.employeeRepository = employeeRepository;
        this.workplaceService = workplaceService;
        this.rankingRepository = rankingRepository;
        this.deepServiceGate = deepServiceGate;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(EmployeeDTO::new).toList();
    }

    public List<RankingDTO> getOwnRankings(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException("employee with id " + employeeId + " does not exists"))
                .getOwnRankings().stream().map(RankingDTO::new).toList();
    }

    public List<EmployeeDTO> getAllEmployeeWithoutWorkplace() {
        return employeeRepository.findByWorkplaceIsNull().stream().map(EmployeeDTO::new).toList();
    }

    public List<ProjectDTO> getAllResponsibleProjectsForEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException("employee with id " + employeeId + " does not exists"))
                .getResponsibleProjects().stream().map(ProjectDTO::new).toList();
    }

    public void createEmployee(EmployeeCreateDTO employeeCreateDTO) {
        Employee newEmployee = employeeRepository.save(new Employee(
                employeeCreateDTO.getName(),
                employeeCreateDTO.getStatus()
        ));

        if(employeeCreateDTO.getWorkplaceId() != null) {
            workplaceService.addEmployeeToWorkplace(newEmployee, employeeCreateDTO.getWorkplaceId());
        }
    }

    @Transactional
    public void updateEmployee(Long employeeId, EmployeeCreateDTO data) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

        if(ValidationUtils.checkFieldIsUpdatedAndValid(employee.getName(), data.getName())) {
            employee.setName(data.getName());
        }

        if( data.getStatus() != null && employee.getStatus() != data.getStatus()) {
            employee.setStatus(data.getStatus());
            if(data.getStatus() == EmployeeStatus.QUIT) {
                deepServiceGate.handle(new EmployeeQuitLog(employee));
            }
        }

        if(data.getWorkplaceId() != null) {
            workplaceService.addEmployeeToWorkplace(employee, data.getWorkplaceId());
        }
    }

    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

        workplaceService.removeEmployeeFromAnyWorkplace(employee);

        List<Ranking> rankingsToDelete = rankingRepository.findAllByRankingEmployeeOrRankedPerson(employee,employee);
        rankingRepository.deleteAll(rankingsToDelete);

        employeeRepository.delete(employee);
    }

    public String checkAssignedEmployessForCompatibility(List<Long> assignedEmployees) {
        StringBuilder returnString = new StringBuilder();
        if(assignedEmployees.isEmpty()) return "";

        for (Long employeeId : assignedEmployees) {
            String textValue = checkWorstRatedPersonsOfId(employeeId,assignedEmployees);
            if(!textValue.isEmpty()) {
                returnString.append(textValue).append(" Auch ");
            }
        }
        return returnString.toString();
    }

    public String checkWorstRatedPersonsOfId(Long rankingEmployeeId, List<Long> assignedEmployees) {
        StringBuilder returnString = new StringBuilder();
        Employee employee = employeeRepository.findById(rankingEmployeeId).orElseThrow();
        List<RankingWorstRankedPersonDisplayDTO> worstRankedEmployees = rankingRepository.getWorstRankedPersonsOfRankingEmployeeById(rankingEmployeeId);

        for (Long employeeId : assignedEmployees) {
            if(Objects.equals(employeeId, rankingEmployeeId)) continue;

            if (worstRankedEmployees.stream().anyMatch(e -> e.getRankedPersonId().equals(employeeId))) {
                if (returnString.isEmpty()) {
                    returnString.append(employee.getName()).append(" arbeitet ungern mit ");
                }
                returnString.append(employeeRepository.findById(employeeId).orElseThrow().getName()).append(" und ");
            }
        }

        return returnString.substring(0, returnString.length() - 5) + ".";
    }
}
