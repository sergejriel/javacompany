package com.exercise.javacompany.DTO;

import com.exercise.javacompany.model.Employee;
import com.exercise.javacompany.model.Workplace;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Data
@NoArgsConstructor
public class WorkplaceDTO {
    private Long id;
    private String description;
    private int monitorCount;
    private Long employeeId;

    public WorkplaceDTO(@NotNull Workplace model) {
        this.id = model.getId();
        this.description = model.getDescription();
        this.monitorCount = model.getMonitorCount();
        this.employeeId = Optional.ofNullable(model.getEmployee())
                .map(Employee::getId)
                .orElse(null);
    }
}
