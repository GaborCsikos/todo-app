package hu.gabor.csikos.todoapp.mapper;

import com.google.common.collect.Lists;
import hu.gabor.csikos.todoapp.dto.UserDTO;
import hu.gabor.csikos.todoapp.dto.UserDTOWithPassword;
import hu.gabor.csikos.todoapp.entity.Role;
import hu.gabor.csikos.todoapp.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    public void toDTO() {

        // Given
        User user = User.builder()
                .id(1L)
                .username("userName")
                .password("Secret") //wont be used
                .roles(Lists.newArrayList(Role.builder().id(1L).name("ADMIN").build()))
                .build();
        // When
        UserDTO result = userMapper.toDTO(user);

        // Then
        assertEquals(1L, result.getId().longValue());
        assertEquals("userName", result.getUsername());
        assertNull(result.getPassword());

    }

    @Test
    public void toEntity() {
        // Given
        UserDTO userDTO = UserDTO.builder()
                .username("userName")
                .password("Secret") //wont be used
                .build();
        when(bCryptPasswordEncoder.encode("Secret")).thenReturn("ABCABC");

        // When
        User result = userMapper.toEntity(userDTO);

        // Then
        assertEquals("userName", result.getUsername());
        assertEquals("ABCABC", result.getPassword());
    }


    @Test
    public void toEntityForUpdate() {
        // Given
        String hashedSavedPW = "Hashed";

        UserDTO userDTO = UserDTO.builder()
                .username("userName")
                .password("Secret") //wont be used
                .id(1L)
                .build();

        // When
        User result = userMapper.toEntityForUpdate(userDTO, hashedSavedPW);

        // Then
        assertEquals(1L, result.getId().longValue());
        assertEquals("userName", result.getUsername());
        assertEquals(hashedSavedPW, result.getPassword());

    }


    @Test

    public void passWordChange() {
        // Given
        String hashedSavedPW = "Hashed";
        String usersEnteredPw = "entered";
        String newPassword = "NewPasswordAlsoHashed";

        UserDTOWithPassword passwordDTO = new UserDTOWithPassword();
        passwordDTO.setOldPassword(usersEnteredPw);
        passwordDTO.setPassword(newPassword);

        when(bCryptPasswordEncoder.matches(usersEnteredPw, hashedSavedPW)).thenReturn(true);
        when(bCryptPasswordEncoder.encode(newPassword)).thenReturn("ABCABC");


        // When
        String newHashedPW = userMapper.newPassword(passwordDTO, hashedSavedPW);

        // Then
        assertEquals(newHashedPW, "ABCABC");
    }


    @Test

    public void passWordChangeInvalidPassword() {
        // Given
        String hashedSavedPW = "Hashed";
        String usersEnteredPw = "entered";
        String newPassword = "NewPasswordAlsoHashed";
        UserDTOWithPassword passwordDTO = new UserDTOWithPassword();
        passwordDTO.setOldPassword(usersEnteredPw);
        passwordDTO.setPassword(newPassword);
        when(bCryptPasswordEncoder.matches(usersEnteredPw, hashedSavedPW)).thenReturn(false);
        // Then
        Assertions.assertThrows(BadCredentialsException.class, () -> userMapper.newPassword(passwordDTO, hashedSavedPW));

    }

}