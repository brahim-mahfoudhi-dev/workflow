package com.workflow.workflowpro.dto;

import com.workflow.workflowpro.entity.TaskStatus;

import java.time.LocalDate;

public record TaskDTO(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDate deadline,
        Long projectId,
        Long assignedToId
) {}

