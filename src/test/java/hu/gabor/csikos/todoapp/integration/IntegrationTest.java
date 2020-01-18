package hu.gabor.csikos.todoapp.integration;

import hu.gabor.csikos.todoapp.TodoappApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TodoappApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class IntegrationTest {
    private static final String ENDPOINT = "/todo";
    @Autowired
    protected TestRestTemplate restTemplate;


    @LocalServerPort
    private int port;

    protected String getRootUrl() {
        return "http://localhost:" + port + ENDPOINT;
    }

    @Test
    public void init() {

    }
}
