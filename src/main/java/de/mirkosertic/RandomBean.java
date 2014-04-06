package de.mirkosertic;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Scope("singleton")
public class RandomBean {

    private final Random random;

    public RandomBean() {
        random = new Random();
    }

    @CircuitBreaker
    public String random() {
        return "Random " + random.nextInt();
    }
}
