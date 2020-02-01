package hu.gabor.csikos.todoapp.mapper;

import hu.gabor.csikos.todoapp.dto.TodoDTO;
import hu.gabor.csikos.todoapp.entity.Priority;
import hu.gabor.csikos.todoapp.entity.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public TodoDTO entityToDTO(Todo todo) {
        return new TodoDTO(todo.getId(), todo.getName(), todo.getPriority().name());
    }

    public Todo dtoToEntity(TodoDTO todo) {
        return new Todo(todo.getId(), todo.getName(), Priority.valueOf(todo.getPriority()));
    }

    public Todo updtate(Todo todoToUpdate, TodoDTO todo) {
        todoToUpdate.setName(todo.getName());
        todoToUpdate.setPriority(Priority.valueOf(todo.getPriority()));
        return todoToUpdate;
    }
}
