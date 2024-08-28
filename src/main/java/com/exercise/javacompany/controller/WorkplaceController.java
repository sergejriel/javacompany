package com.exercise.javacompany.controller;

import com.exercise.javacompany.DTO.WorkplaceDTOs.WorkplaceCreateOrUpdateDTO;
import com.exercise.javacompany.DTO.WorkplaceDTOs.WorkplaceDTO;
import com.exercise.javacompany.service.WorkplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkplaceController
{
    private final WorkplaceService workplaceService;

    @Autowired
    public WorkplaceController(WorkplaceService workplaceService) {
        this.workplaceService = workplaceService;
    }

    @GetMapping("/workplace/{workplaceId}")
    public WorkplaceDTO getWorkplace(
            @PathVariable("workplaceId") Long workplaceId
    ) {
        return workplaceService.getWorkplace(workplaceId);
    }

    @GetMapping("/workplaces")
    public List<WorkplaceDTO> getAllWorkplaces(
    ) {
        return workplaceService.getAllWorkplaces();
    }

    @PostMapping("/workplace")
    public WorkplaceDTO createWorkplace(
            @RequestBody WorkplaceCreateOrUpdateDTO workplaceDTO
    ) {
        return workplaceService.createWorkplace(workplaceDTO);
    }

    @PutMapping("/workplace/{workplaceId}")
    public WorkplaceDTO updateWorkplace(
            @PathVariable("workplaceId") Long workplaceId,
            @RequestBody WorkplaceCreateOrUpdateDTO workplaceCreateOrUpdateDTO
    ) {
        //Die id in workplaceDTO ist somit redundant.
        return workplaceService.updateWorkplace( workplaceId ,workplaceCreateOrUpdateDTO);
    }

    @DeleteMapping("/workplace/{workplaceId}")
    public void deleteWorkplace(
            @PathVariable("workplaceId") Long workplaceId
    ) {
        workplaceService.deleteWorkplace(workplaceId);
    }


}
