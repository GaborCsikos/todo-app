package hu.gabor.csikos.todoapp.integration;

import hu.gabor.csikos.todoapp.dto.UserDTO;
import hu.gabor.csikos.todoapp.dto.UserDTOWithPassword;
import hu.gabor.csikos.todoapp.entity.User;
import hu.gabor.csikos.todoapp.repository.UserRepository;
import hu.gabor.csikos.todoapp.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Sql(
        scripts = {"classpath:/scripts/user.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserServiceTest extends IntegrationTest {

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void register() {
        UserDTO userDTO = UserDTO.builder()
                .username("userName")
                .password("PW")
                .build();
        UserDTO response = userService.register(userDTO);
        assertNotNull(response.getId());
        assertNull(response.getPassword());
        assertFalse("PW".equals(response.getPassword()));
        assertThat(response.getUsername()).isEqualTo(userDTO.getUsername());

        //Password encrypted
        Optional<User> savedInDataBase = userRepository.findById(response.getId());
        assertFalse("PW".equals(savedInDataBase.get().getPassword()));
    }

    @Test

    public void update() {
        User userSaved = userRepository.findById(1L).get();
        UserDTO userDTO = UserDTO.builder()
                .username("another")
                .id(1L)
                .build();

        UserDTO response = userService.update(userDTO);

        assertNotNull(response.getId());
        assertNull(response.getPassword());
        assertThat(response.getUsername()).isEqualTo(userDTO.getUsername());

        //Password is not updated, and stays encrypted
        Optional<User> savedInDataBase = userRepository.findById(response.getId());
        assertThat(userSaved.getPassword()).isEqualTo(savedInDataBase.get().getPassword());

    }


    @Test
    public void updatePassword() {
        User userSaved = userRepository.findById(1L).get();
        String usersEnteredPw = "PW";
        String newPassword = "NewPasswordAlsoHashed";

        UserDTOWithPassword passwordDTO = new UserDTOWithPassword();
        passwordDTO.setOldPassword(usersEnteredPw);
        passwordDTO.setPassword(newPassword);
        passwordDTO.setId(1L);
        UserDTO response = userService.updatePassword(passwordDTO);

        assertNotNull(response.getId());
        assertNull(response.getPassword());

        //Password is updated
        Optional<User> savedInDataBase = userRepository.findById(response.getId());
        assertThat(userSaved.getPassword()).isNotEqualTo(savedInDataBase.get().getPassword());
    }

    @Test
    public void updatePasswordFailing() {
        String usersEnteredPw = "entered";
        String newPassword = "NewPasswordAlsoHashed";
        UserDTOWithPassword passwordDTO = new UserDTOWithPassword();
        passwordDTO.setOldPassword(usersEnteredPw);
        passwordDTO.setPassword(newPassword);
        passwordDTO.setId(1L);
        Assertions.assertThrows(BadCredentialsException.class, () -> userService.updatePassword(passwordDTO));
    }

}