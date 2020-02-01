package hu.gabor.csikos.todoapp.mapper;

import hu.gabor.csikos.todoapp.dto.TodoDTO;
import hu.gabor.csikos.todoapp.entity.Priority;
import hu.gabor.csikos.todoapp.entity.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public TodoDTO entityToDTO(Todo todo) {
        TodoDTO.TodoDTOBuilder builder  =  TodoDTO.builder().id(todo.getId()).name(todo.getName()).priority(todo.getPriority().name());
        if(todo.getDaysToAchieve()!= null){
            builder.daysToAchieve(todo.getDaysToAchieve().getDays());
        }
        return builder.build();
    }

    public Todo dtoToEntity(TodoDTO todo) {
        return Todo.builder().id(todo.getId()).name(todo.getName()).priority( Priority.valueOf(todo.getPriority())).build();
    }

    public Todo updtate(Todo todoToUpdate, TodoDTO todo) {
        todoToUpdate.setName(todo.getName());
        todoToUpdate.setPriority(Priority.valueOf(todo.getPriority()));
        return todoToUpdate;
    }
}
