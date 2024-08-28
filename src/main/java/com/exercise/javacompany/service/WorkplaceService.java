package com.exercise.javacompany.service;

import com.exercise.javacompany.DTO.WorkplaceDTOs.WorkplaceCreateOrUpdateDTO;
import com.exercise.javacompany.DTO.WorkplaceDTOs.WorkplaceDTO;
import com.exercise.javacompany.model.Employee;
import com.exercise.javacompany.model.Workplace;
import com.exercise.javacompany.repository.EmployeeRepository;
import com.exercise.javacompany.repository.WorkplaceRepository;
import com.exercise.javacompany.utils.ValidationUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WorkplaceService {

    private final WorkplaceRepository workplaceRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public WorkplaceService(
            WorkplaceRepository workplaceRepository,
            EmployeeRepository employeeRepository
                            ) {
        this.workplaceRepository = workplaceRepository;
        this.employeeRepository = employeeRepository;
    }

    public WorkplaceDTO getWorkplace(Long workplaceID){
        Optional<Workplace> workplace = workplaceRepository.findById(workplaceID);

        if(workplace.isPresent()) {
            return new WorkplaceDTO(workplace.get());
        } else {
            throw new IllegalStateException("workplace with id " + workplaceID + " foes not exists");
        }
    }

    public List<WorkplaceDTO> getAllWorkplaces() {
        List<Workplace> workplaces = workplaceRepository.findAll();

        return workplaces.stream()
                .map(WorkplaceDTO::new)// Äquvivalent zu ".map(workplace -> new WorkplaceDTO(workplace))
                .toList(); //.toList gibt eine Liste zurück, die nicht veränderbar ist und kam ab Java 10. Für eine veränderbare Liste sollte man weiterhin das collect(Collectors.toList()) verwenden
    }

    public WorkplaceDTO createWorkplace(@NotNull WorkplaceCreateOrUpdateDTO workplaceCreateOrUpdateDTO) {
        Employee employee = null;

        //TODO SR: Behandle den Fall, falls der Employee bereits zu einen anderen Workplace zugewiesen ist.

        if(workplaceCreateOrUpdateDTO.getEmployeeId() != null) {
            employee = employeeRepository.findById(workplaceCreateOrUpdateDTO.getEmployeeId()).orElseThrow(
                    () -> new IllegalArgumentException("Employee not found with ID: " + workplaceCreateOrUpdateDTO.getEmployeeId())
            );

            setPrevEmployeeWorkplaceToNullIfNecessary(employee);
        }

        Workplace workplace = new Workplace(
                workplaceCreateOrUpdateDTO.getDescription(),
                workplaceCreateOrUpdateDTO.getMonitorCount(),
                employee);

        return new WorkplaceDTO(workplaceRepository.save(workplace));
    }

    @Transactional // Es sorgt dafür, dass das geholte workplace beim set automatisch auf die DB gespeichert wird.
    public WorkplaceDTO updateWorkplace(
            Long workplaceId,
            @NotNull WorkplaceCreateOrUpdateDTO workplaceCreateOrUpdateDTO
    ) {

        // Das .findById macht immer ein DB call und gibt Optional zurück.
        // .getReferenceById ist Lazy,
        // Wenn die angegebene ID für getReferenceById ungültig ist
        // (d.h., es existiert kein Eintrag in der Datenbank mit dieser ID),
        // wird eine EntityNotFoundException geworfen beim ersten Zugriff auf die Eigenschaften dieser Entität,
        // nicht beim Aufruf von getReferenceById.
        Workplace workplace = workplaceRepository.findById(workplaceId).orElseThrow(
                        () -> new IllegalArgumentException(
                                "Employee not found with ID: " + workplaceCreateOrUpdateDTO.getEmployeeId()
                        ));

        if(ValidationUtils.checkFieldIsUpdatedAndValid(workplace.getDescription(), workplaceCreateOrUpdateDTO.getDescription())) {
            workplace.setDescription(workplaceCreateOrUpdateDTO.getDescription());
        }

        if (workplace.getMonitorCount() != workplaceCreateOrUpdateDTO.getMonitorCount()) {
            workplace.setMonitorCount(workplaceCreateOrUpdateDTO.getMonitorCount());
        }

        if (workplaceCreateOrUpdateDTO.getEmployeeId() != null) {
/*            Employee employee = employeeRepository.findById(workplaceCreateOrUpdateDTO.getEmployeeId()).orElseThrow(
                    () -> new IllegalArgumentException(
                            "Employee not found with ID: " + workplaceCreateOrUpdateDTO.getEmployeeId()
                    )
            );
            workplace.setEmployee(employee);*/

            // Die try catch Variante:
            try {
                Employee employee = employeeRepository.getReferenceById(workplaceCreateOrUpdateDTO.getEmployeeId());

                if(workplace.getEmployee() == null
                        || !Objects.equals(
                                workplace.getEmployee(),
                                employee
                )) {
                    setPrevEmployeeWorkplaceToNullIfNecessary(employee);
                    workplace.setEmployee(employee);
                }
            } catch (EntityNotFoundException e) {
                throw new IllegalArgumentException("Employee not found with ID: " + workplaceCreateOrUpdateDTO.getEmployeeId(), e);
            }
        } else {
            workplace.setEmployee(null);
        }

        return new WorkplaceDTO(workplace);
    }

    public void deleteWorkplace(Long workplaceId) {
        if (workplaceRepository.findById(workplaceId).isPresent()) {
            workplaceRepository.deleteById(workplaceId);
        } else {
            throw new IllegalArgumentException("Workplace not found with ID: " + workplaceId);
        }
    }

    private void setPrevEmployeeWorkplaceToNullIfNecessary(Employee employee) {

        workplaceRepository.findByEmployee(employee).ifPresent(workplace -> {
            workplace.setEmployee(null);
            workplaceRepository.save(workplace);
        });
    }

}
