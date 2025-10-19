package com.workflow.workflowpro.dto;

import java.time.LocalDate;

public record ProjectDTO(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        Long managerId
) {}

