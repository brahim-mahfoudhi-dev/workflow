package com.workflow.workflowpro.service.user;

import com.workflow.workflowpro.dto.UserDTO;
import com.workflow.workflowpro.entity.User;
import com.workflow.workflowpro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserDTO register(UserDTO user) {
        if (userRepository.existsById(user.id())) {
            throw new RuntimeException("User already exists!");
        }
        User newUser = User.builder()
                .id(user.id())
                .username(user.username())
                .email(user.email())
                .role(user.role())
                .build();

        userRepository.save(newUser);
        return mapToDTO(newUser);
    }

    @Override
    public UserDTO findById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User does not exist"));
        return mapToDTO(user);
    }

    @Override
    public List<UserDTO> listAll() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO mapToDTO(User newUser) {
        return new UserDTO(
                newUser.getId(),
                newUser.getUsername(),
                newUser.getEmail(),
                newUser.getRole()
        );
    }
}
