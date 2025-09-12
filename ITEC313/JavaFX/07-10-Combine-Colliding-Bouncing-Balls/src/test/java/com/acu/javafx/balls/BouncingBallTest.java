package com.acu.javafx.balls;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BouncingBallTest {
    @Test
    void testCollisionAndAbsorb() {
        BouncingBall a = new BouncingBall(50, 50, 10, 0, 0, Color.GRAY);
        BouncingBall b = new BouncingBall(60, 50, 8, 0, 0, Color.GRAY);
        assertTrue(a.collides(b));
        a.absorbRadius(b.radius);
        assertEquals(18, a.radius, 1e-9);
    }

    @Test
    void testMoveBounce() {
        BouncingBall a = new BouncingBall(5, 5, 5, -2, -3, Color.GRAY);
        a.move(100, 100);
        // Should bounce off both walls and remain within bounds
        assertTrue(a.node.getCenterX() >= a.radius);
        assertTrue(a.node.getCenterY() >= a.radius);
    }
}


