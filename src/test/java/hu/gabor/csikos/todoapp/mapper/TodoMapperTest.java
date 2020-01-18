package hu.gabor.csikos.todoapp.mapper;

import hu.gabor.csikos.todoapp.dto.TodoDTO;
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
        Todo todo = new Todo(1L, "test");
        TodoDTO result = mapper.entityToDTO(todo);
        assertEquals(1L, result.getId());
        assertEquals("test", result.getName());
    }

    @Test
    void dtoToEntity() {
        TodoDTO todoDTO = new TodoDTO(1L, "test");
        Todo result = mapper.dtoToEntity(todoDTO);
        assertEquals(1L, result.getId());
        assertEquals("test", result.getName());
    }

    @Test
    void updtate() {
        Todo todo = new Todo(1L, "test");
        TodoDTO todoDTO = new TodoDTO(1L, "update");
        Todo result = mapper.updtate(todo, todoDTO);
        assertEquals(1L, result.getId());
        assertEquals("update", result.getName());
    }
}