package hu.gabor.csikos.todoapp.service;

import hu.gabor.csikos.todoapp.dto.TodoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {
    Page<TodoDTO> getAllTodos(Pageable pageable);

    TodoDTO getTododById(long id);

    TodoDTO createTodo(TodoDTO todo);

    TodoDTO updateTodo(long id, TodoDTO todo);

    void deleteId(long id);
}
