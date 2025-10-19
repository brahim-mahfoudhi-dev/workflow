package com.workflow.workflowpro.controller;

import com.workflow.workflowpro.dto.UserDTO;
import com.workflow.workflowpro.repository.UserRepository;
import com.workflow.workflowpro.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserServiceImpl userService;

    @RequestMapping("/{id}")
    public UserDTO getById(@PathVariable long id) {
        return userService.findById(id);
    }

    public List<UserDTO> getAll() {
        return userService.listAll();
    }
}
