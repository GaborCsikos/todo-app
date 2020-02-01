package hu.gabor.csikos.todoapp.integration;

import hu.gabor.csikos.todoapp.cache.CachedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmbeddedRedisTest extends IntegrationTest{

    @Autowired
    private CachedService cachedService;

    @Test
    void testRedisCache() {
        int result = cachedService.getCounter();
        assertEquals(1, result);
        int result2 = cachedService.getCounter();
        assertEquals(1, result2);
    }
}
