package com.acu.javafx.augraph;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraTest {
    @Test
    public void simplePath() {
        Graph g = new Graph();
        g.addCity(new Graph.CityNode("A", 0, 0));
        g.addCity(new Graph.CityNode("B", 0, 0));
        g.addCity(new Graph.CityNode("C", 0, 0));
        g.addUndirectedEdge("A", "B", 2);
        g.addUndirectedEdge("B", "C", 2);
        g.addUndirectedEdge("A", "C", 10);

        List<String> path = Dijkstra.shortestPath(g, "A", "C");
        assertEquals(List.of("A", "B", "C"), path);
    }
}


