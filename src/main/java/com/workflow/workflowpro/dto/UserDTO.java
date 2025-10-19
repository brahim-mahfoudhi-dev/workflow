package com.workflow.workflowpro.dto;

import com.workflow.workflowpro.entity.Role;

public record UserDTO(
        Long id,
        String username,
        String email,
        Role role
) {}

