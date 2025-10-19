package com.workflow.workflowpro.service.auth;

import com.workflow.workflowpro.dto.AuthRequestDTO;
import com.workflow.workflowpro.dto.AuthResponseDTO;
import com.workflow.workflowpro.dto.RegisterRequestDTO;
import com.workflow.workflowpro.entity.User;
import com.workflow.workflowpro.repository.UserRepository;
import com.workflow.workflowpro.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;


    @Transactional
    @Override
    public AuthResponseDTO register(RegisterRequestDTO requestDTO) {
        if (userRepository.existsByUsername(requestDTO.username())) {
            throw new IllegalArgumentException("The user is already registered!");
        }


        User user = User.builder()
                .username(requestDTO.username())
                .email(requestDTO.email())
                .password(passwordEncoder.encode(requestDTO.password()))
                .role(requestDTO.role())
                .build();


        User savedUser = userRepository.saveAndFlush(user);
        System.out.println("User saved with ID: " + savedUser.getId());
        String token = generateToken(user.getUsername());
        return mapToDTO(user, token);
    }


    @Override
    public AuthResponseDTO login(AuthRequestDTO requestDTO) {
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       requestDTO.username(),
                       requestDTO.password()
               )
       );

        User user = userRepository.findByUsername(requestDTO.username()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = generateToken(user.getUsername());
        return mapToDTO(user, token);
    }

    @Override
    public AuthResponseDTO refreshToken(String refreshToken) {
        String username = JwtTokenProvider.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String newToken = tokenProvider.generateToken(username);
        return mapToDTO(user, newToken);
    }

    private AuthResponseDTO mapToDTO(User user, String token) {
        return new AuthResponseDTO(
                token,
                user.getUsername(),
                user.getRole()
        );
    }

    private String generateToken(String username) {
        return tokenProvider.generateToken(username);

    }
}
