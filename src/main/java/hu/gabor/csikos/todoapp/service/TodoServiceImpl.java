package hu.gabor.csikos.todoapp.service;

import hu.gabor.csikos.todoapp.dto.TodoDTO;
import hu.gabor.csikos.todoapp.entity.Todo;
import hu.gabor.csikos.todoapp.exception.ResourceNotFoundException;
import hu.gabor.csikos.todoapp.mapper.TodoMapper;
import hu.gabor.csikos.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoMapper mapper;


    @Override
    public Page<TodoDTO> getAllTodos(Pageable pageable) {
        return todoRepository.findAll(pageable).map(entity -> mapper.entityToDTO(entity));
    }


    @Override
    public TodoDTO getTododById(long id) {
        Optional<Todo> found = todoRepository.findById(id);
        if (found.isPresent()) {
            return mapper.entityToDTO(found.get());
        } else {
            throw new ResourceNotFoundException("Todo not found");
        }

    }

    @Override
    public TodoDTO createTodo(TodoDTO todo) {
        Todo todoEntity = mapper.dtoToEntity(todo);
        return mapper.entityToDTO(todoRepository.save(todoEntity));
    }

    @Override
    public TodoDTO updateTodo(long id, TodoDTO todo) {
        Todo todoToUpdate = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Update failed"));
        Todo updatedTodo = mapper.updtate(todoToUpdate, todo);
        return mapper.entityToDTO(todoRepository.save(updatedTodo));
    }

    @Override
    public void deleteId(long id) {
        todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Delete failed"));
        todoRepository.deleteById(id);
    }
}
