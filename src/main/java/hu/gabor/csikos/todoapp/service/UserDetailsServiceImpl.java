package hu.gabor.csikos.todoapp.service;

import hu.gabor.csikos.todoapp.dto.UserDTO;
import hu.gabor.csikos.todoapp.dto.UserDTOWithPassword;
import hu.gabor.csikos.todoapp.entity.User;
import hu.gabor.csikos.todoapp.exception.ResourceNotFoundException;
import hu.gabor.csikos.todoapp.mapper.UserMapper;
import hu.gabor.csikos.todoapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@Primary
public class UserDetailsServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return user.get();
    }


    @Override
    public UserDTO update(@NotNull UserDTO userDTO) {
        Optional<User> byId = userRepository.findById(userDTO.getId());
        if (byId.isEmpty()) {
            throw new ResourceNotFoundException("Todo not found");
        }
        User saved = userRepository.save(userMapper.toEntityForUpdate(userDTO, byId.get().getPassword()));
        return userMapper.toDTO(saved);
    }


    @Override
    public UserDTO updatePassword(@NotNull UserDTOWithPassword userDTO) {
        Optional<User> byId = userRepository.findById(userDTO.getId());
        if (byId.isEmpty()) {
            throw new ResourceNotFoundException("Todo not found");
        }
        User user = byId.get();
        user.setPassword(userMapper.newPassword(userDTO, byId.get().getPassword()));
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }


    @Override
    public UserDTO register(@NotNull UserDTO userDTO) {
        if (userDTO.getId() != null) {
            throw new ResourceNotFoundException("Todo not found");
        }
        User saved = userRepository.save(userMapper.toEntity(userDTO));

        return userMapper.toDTO(saved);
    }

}