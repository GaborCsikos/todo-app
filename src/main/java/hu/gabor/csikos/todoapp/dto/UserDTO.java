package hu.gabor.csikos.todoapp.dto;

import hu.gabor.csikos.todoapp.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private String password;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.id = user.getId();
    }
}

