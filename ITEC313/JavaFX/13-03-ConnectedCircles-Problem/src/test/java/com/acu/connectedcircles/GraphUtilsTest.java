package com.acu.connectedcircles;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GraphUtilsTest {
    @Test
    void singleCircleIsConnected() {
        var a = new CircleItem(0,0,20, Color.RED);
        assertTrue(GraphUtils.isConnected(List.of(a)));
    }

    @Test
    void twoOverlappingAreConnected() {
        var a = new CircleItem(0,0,20, Color.RED);
        var b = new CircleItem(30,0,20, Color.RED); // 30 < 40, overlap
        assertTrue(GraphUtils.isConnected(List.of(a,b)));
    }

    @Test
    void twoSeparatedAreNotConnected() {
        var a = new CircleItem(0,0,10, Color.RED);
        var b = new CircleItem(100,0,10, Color.RED);
        assertFalse(GraphUtils.isConnected(List.of(a,b)));
    }
}


