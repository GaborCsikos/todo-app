package hu.gabor.csikos.todoapp.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import hu.gabor.csikos.todoapp.dto.TodoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TodoappApplicationTests extends IntegrationTest {


    @Test
    public void getAllTodos() throws JsonProcessingException {
        //Given
        ParameterizedTypeReference<List<TodoDTO>> typeRef =
                new ParameterizedTypeReference<List<TodoDTO>>() {
                };

        //When
        ResponseEntity<List<TodoDTO>> todo = restTemplate.exchange(getRootUrl() + "/", HttpMethod.GET,
                null, typeRef);

        //Then
        assertEquals(HttpStatus.OK, todo.getStatusCode());
        assertEquals(1, todo.getBody().get(0).getId());
        assertEquals("test", todo.getBody().get(0).getName());
    }


    @Test
    public void getTodoById() throws JsonProcessingException {
        //When
        ResponseEntity<TodoDTO> todo = restTemplate.getForEntity(getRootUrl() + "/2", TodoDTO.class);

        //Then
        assertEquals(HttpStatus.OK, todo.getStatusCode());
        assertEquals(2, todo.getBody().getId());
        assertEquals("update", todo.getBody().getName());
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
        TodoDTO dto = new TodoDTO(null, "example");

        //When
        ResponseEntity<TodoDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/", dto, TodoDTO.class);


        //Then
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody().getId());
        assertEquals("example", postResponse.getBody().getName());
    }

    @Test
    public void updateTodo() {
        //Given
        TodoDTO dto = new TodoDTO(1L, "updated");

        //When
        restTemplate.put(getRootUrl() + "/1", dto, TodoDTO.class);

        //Then
        ResponseEntity<TodoDTO> todo = restTemplate.getForEntity(getRootUrl() + "/1", TodoDTO.class);

        assertEquals(HttpStatus.OK, todo.getStatusCode());
        assertEquals(1, todo.getBody().getId());
        assertEquals("updated", todo.getBody().getName());
    }

    @Test
    public void noItemToUpdate() {
        //Given
        TodoDTO dto = new TodoDTO(6L, "updated");

        //Then
        restTemplate.put(getRootUrl() + "/6", dto, TodoDTO.class);

        //Then
        ResponseEntity<TodoDTO> todo = restTemplate.getForEntity(getRootUrl() + "/1", TodoDTO.class);
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
