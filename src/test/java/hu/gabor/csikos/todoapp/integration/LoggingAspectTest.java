package hu.gabor.csikos.todoapp.integration;



import hu.gabor.csikos.todoapp.dto.TodoDTO;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggingAspectTest extends IntegrationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testLogging(){
        //When
        ResponseEntity<TodoDTO> todo = restTemplate.getForEntity(getRootUrl() + "/2", TodoDTO.class);
        assertTrue(outContent.toString().contains("Time Taken by"));
    }
}
