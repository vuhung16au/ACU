package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Simple non-JavaFX-launch test just to validate JUnit wiring
class AppBasicTest {
    @Test
    void sampleTest() {
        assertEquals(2, 1 + 1, "Basic arithmetic should work");
    }
}
