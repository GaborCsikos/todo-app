package hu.gabor.csikos.todoapp.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachedService {

    private static int COUNTER = 0;

    @Cacheable("counter")
    public int getCounter() {
        COUNTER++;
        return COUNTER;
    }
}
