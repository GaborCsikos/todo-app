package hu.gabor.csikos.todoapp.mapper;

import hu.gabor.csikos.todoapp.dto.TodoDTO;
import hu.gabor.csikos.todoapp.entity.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public TodoDTO entityToDTO(Todo todo) {
        return new TodoDTO(todo.getId(), todo.getName());
    }

    public Todo dtoToEntity(TodoDTO todo) {
        return new Todo(todo.getId(), todo.getName());
    }

    public Todo updtate(Todo todoToUpdate, TodoDTO todo) {
        todoToUpdate.setName(todo.getName());
        return todoToUpdate;
    }
}
