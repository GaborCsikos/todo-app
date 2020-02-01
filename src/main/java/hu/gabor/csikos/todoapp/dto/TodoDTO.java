package hu.gabor.csikos.todoapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDTO {

    private Long id;
    private String name;
    private String priority;
    private int daysToAchieve;

    public TodoDTO(Long id, String name, String priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }
}
