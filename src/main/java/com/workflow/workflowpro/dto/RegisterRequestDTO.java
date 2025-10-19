package com.workflow.workflowpro.dto;

import com.workflow.workflowpro.entity.Role;

/**
 * DTO used for user registration requests.
 */

public record RegisterRequestDTO (

        String username,
        String email,
        String password,
        Role role

){}
