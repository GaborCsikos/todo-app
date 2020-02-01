package hu.gabor.csikos.todoapp.service;

import hu.gabor.csikos.todoapp.dto.TodoDTO;
import hu.gabor.csikos.todoapp.entity.Priority;
import hu.gabor.csikos.todoapp.entity.Todo;
import hu.gabor.csikos.todoapp.exception.ResourceNotFoundException;
import hu.gabor.csikos.todoapp.mapper.TodoMapper;
import hu.gabor.csikos.todoapp.repository.TodoRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @InjectMocks
    private TodoServiceImpl todoService;

    @Mock
    private TodoRepository repository;

    @Mock
    private TodoMapper mapper;

    @Test
    void getAllTodos() {
        //Given
        Todo todo = new Todo(1L, "test", Priority.LOW);
        when(repository.findAll()).thenReturn(Lists.newArrayList(todo));
        when(mapper.entityToDTO(todo)).thenReturn(new TodoDTO(1L, "test", Priority.LOW.name()));
        //When
        List<TodoDTO> result = todoService.getAllTodos();

        //Then
        assertEquals(1L, result.get(0).getId());
        assertEquals("test", result.get(0).getName());
        assertEquals(Priority.LOW.name(), result.get(0).getPriority());

    }

    @Test
    void getTododById() {
        //Given
        Todo todo = new Todo(1L, "test", Priority.MEDIUM);
        when(repository.findById(1L)).thenReturn(Optional.of(todo));
        when(mapper.entityToDTO(todo)).thenReturn(new TodoDTO(1L, "test", Priority.MEDIUM.name()));
        //When
        TodoDTO result = todoService.getTododById(1L);

        //Then
        assertEquals(1L, result.getId());
        assertEquals("test", result.getName());
        assertEquals(Priority.MEDIUM.name(), result.getPriority());

    }

    @Test
    void getByIdNotFound() {
        //Given
        Todo todo = new Todo(1L, "test", Priority.HIGH);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //When
        Assertions.assertThrows(ResourceNotFoundException.class, () -> todoService.getTododById(1L));

    }


    @Test
    void createTodo() {
        //Given
        TodoDTO todoDTO = new TodoDTO(1L, "test", Priority.HIGH.name());
        Todo todo = new Todo(1L, "test", Priority.HIGH);

        when(repository.save(todo)).thenReturn(todo);
        when(mapper.entityToDTO(todo)).thenReturn(todoDTO);
        when(mapper.dtoToEntity(todoDTO)).thenReturn(todo);

        //When
        TodoDTO result = todoService.createTodo(todoDTO);

        //Then
        assertEquals(1L, result.getId());
        assertEquals("test", result.getName());
        assertEquals(Priority.HIGH.name(), result.getPriority());

    }

    @Test
    void updateTodo() {
        //Given
        TodoDTO todoDTO = new TodoDTO(1L, "update", Priority.HIGH.name());
        Todo todo = new Todo(1L, "test", Priority.HIGH);
        Todo upDated = new Todo(1L, "update", Priority.HIGH);

        when(repository.save(upDated)).thenReturn(upDated);
        when(repository.findById(1L)).thenReturn(Optional.of(todo));
        when(mapper.entityToDTO(upDated)).thenReturn(todoDTO);
        when(mapper.updtate(todo, todoDTO)).thenReturn(upDated);
        //When
        TodoDTO result = todoService.updateTodo(1L, todoDTO);

        //Then
        assertEquals(1L, result.getId());
        assertEquals("update", result.getName());
        assertEquals(Priority.HIGH.name(), result.getPriority());

    }

    @Test
    void updateTodoNotFound() {
        //Given
        when(repository.findById(2L)).thenReturn(Optional.empty());

        //When
        Assertions.assertThrows(ResourceNotFoundException.class, () -> todoService.updateTodo(2L, new TodoDTO()));

    }

    @Test
    void deleteId() {
        //Given
        Todo todo = new Todo(1L, "test", Priority.LOW);
        when(repository.findById(1L)).thenReturn(Optional.of(todo));

        //When
        todoService.deleteId(1L);
    }

    @Test
    void deleteIdNotFound() {
        //Given
        when(repository.findById(1L)).thenThrow(new ResourceNotFoundException("delete failed"));

        //When
        Assertions.assertThrows(ResourceNotFoundException.class, () -> todoService.deleteId(1L));
    }
}