package com.workflow.workflowpro.service.auth;

import com.workflow.workflowpro.dto.AuthRequestDTO;
import com.workflow.workflowpro.dto.AuthResponseDTO;
import com.workflow.workflowpro.dto.RegisterRequestDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO requestDTO);

    AuthResponseDTO login(AuthRequestDTO requestDTO);

    AuthResponseDTO refreshToken(String refreshToken);

}
