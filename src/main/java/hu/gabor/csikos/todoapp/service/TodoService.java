package hu.gabor.csikos.todoapp.service;

import hu.gabor.csikos.todoapp.dto.TodoDTO;

import java.util.List;

public interface TodoService {
    List<TodoDTO> getAllTodos();

    TodoDTO getTododById(long id);

    TodoDTO createTodo(TodoDTO todo);

    TodoDTO updateTodo(long id, TodoDTO todo);

    void deleteId(long id);
}
