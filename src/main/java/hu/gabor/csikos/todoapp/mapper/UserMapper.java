package hu.gabor.csikos.todoapp.mapper;

import hu.gabor.csikos.todoapp.dto.UserDTO;
import hu.gabor.csikos.todoapp.dto.UserDTOWithPassword;
import hu.gabor.csikos.todoapp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserMapper {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDTO toDTO(User entity) {
        UserDTO dto = new UserDTO(entity);
        return dto;

    }

    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        return user;

    }

    public User toEntityForUpdate(UserDTO dto, String hashedPassword) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(hashedPassword);
        user.setId(dto.getId());
        return user;
    }

    public String newPassword(UserDTOWithPassword userDTO, String hashedPassword) {
        if (bCryptPasswordEncoder.matches(userDTO.getOldPassword(), hashedPassword)) {
            return bCryptPasswordEncoder.encode(userDTO.getPassword());
        }
        log.error("Password does not match");
        throw new BadCredentialsException("Password does not match");
    }

}