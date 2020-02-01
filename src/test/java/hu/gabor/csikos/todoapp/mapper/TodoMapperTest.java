package hu.gabor.csikos.todoapp.mapper;

import hu.gabor.csikos.todoapp.dto.TodoDTO;
import hu.gabor.csikos.todoapp.entity.Priority;
import hu.gabor.csikos.todoapp.entity.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TodoMapperTest {

    @InjectMocks
    private TodoMapper mapper;


    @Test
    void entityToDTO() {
        Todo todo = new Todo(1L, "test", Priority.LOW);
        TodoDTO result = mapper.entityToDTO(todo);
        assertEquals(1L, result.getId());
        assertEquals("test", result.getName());
        assertEquals(Priority.LOW.name(), result.getPriority());

    }

    @Test
    void dtoToEntity() {
        TodoDTO todoDTO = new TodoDTO(1L, "test", Priority.HIGH.name());
        Todo result = mapper.dtoToEntity(todoDTO);
        assertEquals(1L, result.getId());
        assertEquals("test", result.getName());
        assertEquals(Priority.HIGH, result.getPriority());

    }

    @Test
    void updtate() {
        Todo todo = new Todo(1L, "test", Priority.LOW);
        TodoDTO todoDTO = new TodoDTO(1L, "update", Priority.MEDIUM.name());
        Todo result = mapper.updtate(todo, todoDTO);
        assertEquals(1L, result.getId());
        assertEquals("update", result.getName());
        assertEquals(Priority.MEDIUM, result.getPriority());
    }
}