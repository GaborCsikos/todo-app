package hu.gabor.csikos.todoapp.service;

import hu.gabor.csikos.todoapp.dto.UserDTO;
import hu.gabor.csikos.todoapp.dto.UserDTOWithPassword;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDTO register(UserDTO user);

    UserDTO update(UserDTO user);

    UserDTO updatePassword(UserDTOWithPassword user);

}