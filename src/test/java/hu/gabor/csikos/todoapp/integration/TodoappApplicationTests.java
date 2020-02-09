package hu.gabor.csikos.todoapp.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import hu.gabor.csikos.todoapp.dto.TodoDTO;
import hu.gabor.csikos.todoapp.entity.Priority;
import hu.gabor.csikos.todoapp.helper.RestPageImpl;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TodoappApplicationTests extends IntegrationTest {


    @Test
    public void getAllTodos() {
        //Given
        ParameterizedTypeReference<RestPageImpl<TodoDTO>> typeRef =
                new ParameterizedTypeReference<RestPageImpl<TodoDTO>>() {
                };

        //When
        ResponseEntity<RestPageImpl<TodoDTO>> todo = restTemplate.exchange(getRootUrl() + "/list", HttpMethod.GET,
                null, typeRef);

        //Then
        assertEquals(HttpStatus.OK, todo.getStatusCode());
        TodoDTO dto = todo.getBody().get().filter(x -> x.getId() == 1).findFirst().get();
        assertEquals(1, dto.getId());
        assertEquals("test", dto.getName());
        assertEquals(Priority.MEDIUM.name(), dto.getPriority());

        //Test One to One
        assertEquals(12, dto.getDaysToAchieve());

        //Test One to Many
        assertEquals("Must do today", dto.getNotes().get(0));
        assertEquals("I need coffee", dto.getNotes().get(1));

        //Test Many to Many
        assertEquals("World domination", dto.getGoals().get(0));
    }

    @Test
    public void testPagination() {
        //Given
        ParameterizedTypeReference<RestPageImpl<TodoDTO>> typeRef =
                new ParameterizedTypeReference<RestPageImpl<TodoDTO>>() {
                };

        //When
        ResponseEntity<RestPageImpl<TodoDTO>> todo = restTemplate.exchange(getRootUrl() + "/list?page=0&size=10", HttpMethod.GET,
                null, typeRef);
        //Then
        assertEquals(HttpStatus.OK, todo.getStatusCode());
        TodoDTO dto = todo.getBody().get().filter(x -> x.getId() == 2).findFirst().get();
        assertEquals(2, dto.getId());
        assertEquals("update", dto.getName());
        assertEquals(Priority.MEDIUM.name(), dto.getPriority());
        assertEquals(0, todo.getBody().getNumber());
        assertEquals(10, todo.getBody().getSize());
        assertEquals(3, todo.getBody().getTotalElements());
        assertEquals(1, todo.getBody().getTotalPages());
    }

    @Test
    public void getTodoById() throws JsonProcessingException {
        //When
        ResponseEntity<TodoDTO> todo = restTemplate.getForEntity(getRootUrl() + "/2", TodoDTO.class);

        //Then
        assertEquals(HttpStatus.OK, todo.getStatusCode());
        assertEquals(2, todo.getBody().getId());
        assertEquals("update", todo.getBody().getName());
        assertEquals(Priority.MEDIUM.name(), todo.getBody().getPriority());

    }

    @Test
    public void getNotExistingTodo() {

        //When
        ResponseEntity<TodoDTO> todo = restTemplate.getForEntity(getRootUrl() + "/8", TodoDTO.class);

        //Then
        assertEquals(HttpStatus.NOT_FOUND, todo.getStatusCode());
    }

    @Test
    public void createTodo() {
        //Given
        TodoDTO dto = new TodoDTO(null, "example", Priority.HIGH.name());

        //When
        ResponseEntity<TodoDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/", dto, TodoDTO.class);


        //Then
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody().getId());
        assertEquals("example", postResponse.getBody().getName());
        assertEquals("example", postResponse.getBody().getName());
        assertEquals(Priority.HIGH.name(), postResponse.getBody().getPriority());

    }

    @Test
    public void invalidEnum() {
        //Given
        TodoDTO dto = new TodoDTO(null, "example", "Not valid");

        //When
        ResponseEntity<TodoDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/", dto, TodoDTO.class);


        //Then
        assertEquals(HttpStatus.NOT_FOUND, postResponse.getStatusCode());
    }

    @Test
    public void updateTodo() {
        //Given
        TodoDTO dto = new TodoDTO(1L, "updated", Priority.LOW.name());

        //When
        restTemplate.put(getRootUrl() + "/1", dto, TodoDTO.class);

        //Then
        ResponseEntity<TodoDTO> todo = restTemplate.getForEntity(getRootUrl() + "/1", TodoDTO.class);

        assertEquals(HttpStatus.OK, todo.getStatusCode());
        assertEquals(1, todo.getBody().getId());
        assertEquals("updated", todo.getBody().getName());
        assertEquals(Priority.LOW.name(), todo.getBody().getPriority());

    }

    @Test
    public void noItemToUpdate() {
        //Given
        TodoDTO dto = new TodoDTO(6L, "updated", Priority.MEDIUM.name());

        //Then
        restTemplate.put(getRootUrl() + "/6", dto, TodoDTO.class);

        //Then
        ResponseEntity<TodoDTO> todo = restTemplate.getForEntity(getRootUrl() + "/6", TodoDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, todo.getStatusCode());
    }


    @Test
    public void noItemToDelete() {
        try {
            restTemplate.delete(getRootUrl() + "/6");
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }

        //Then

    }

    @Test
    public void testDeletePost() {
        int id = 1;

        restTemplate.delete(getRootUrl() + "/" + id);

        try {
            restTemplate.getForEntity(getRootUrl() + "/" + id, TodoDTO.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    private HttpEntity<String> createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<String>(null, headers);
    }
}
