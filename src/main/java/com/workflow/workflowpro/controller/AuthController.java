package com.workflow.workflowpro.controller;

import com.workflow.workflowpro.dto.AuthRequestDTO;
import com.workflow.workflowpro.dto.AuthResponseDTO;
import com.workflow.workflowpro.dto.RegisterRequestDTO;
import com.workflow.workflowpro.service.auth.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO) {
        System.out.println("Received DTO: " + requestDTO);
        AuthResponseDTO response = authService.register(requestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponseDTO dto = authService.login(authRequestDTO);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("Controller is working");
        return "ok";
    }

}
