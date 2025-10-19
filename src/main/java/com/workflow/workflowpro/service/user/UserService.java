package com.workflow.workflowpro.service.user;


import com.workflow.workflowpro.dto.UserDTO;

import java.util.List;

public interface UserService {

     UserDTO register(UserDTO user);

     UserDTO findById(long userId);

     List<UserDTO> listAll();
}
