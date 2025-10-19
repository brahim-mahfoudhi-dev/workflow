package com.workflow.workflowpro.dto;

import com.workflow.workflowpro.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used for user registration requests.
 * Includes all necessary fields to create a new user.
 */

public record RegisterRequestDTO (

        String username,
        String email,
        String password,
        Role role // optional, e.g. "ADMIN" or "USER"

){}
