package com.workflow.workflowpro.dto;

import com.workflow.workflowpro.entity.Role;

public record AuthResponseDTO(
        String token,
        String username,
        Role role
) {}
