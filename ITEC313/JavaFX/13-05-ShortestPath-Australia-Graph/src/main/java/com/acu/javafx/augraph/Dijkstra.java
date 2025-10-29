package com.acu.javafx.augraph;

import java.util.*;

/**
 * Dijkstra shortest path algorithm for non-negative weights.
 */
public final class Dijkstra {
    private Dijkstra() {}

    public static List<String> shortestPath(Graph g, String source, String target) {
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        for (String c : g.getCities()) {
            dist.put(c, Double.POSITIVE_INFINITY);
        }
        dist.put(source, 0.0);

        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));
        pq.add(source);

        while (!pq.isEmpty()) {
            String u = pq.poll();
            if (u.equals(target)) break;
            for (Graph.Edge e : g.neighbors(u)) {
                double alt = dist.get(u) + e.weight;
                if (alt < dist.get(e.to)) {
                    dist.put(e.to, alt);
                    prev.put(e.to, u);
                    pq.remove(e.to);
                    pq.add(e.to);
                }
            }
        }

        LinkedList<String> path = new LinkedList<>();
        String step = target;
        if (!prev.containsKey(step) && !source.equals(target)) return List.of();
        path.addFirst(step);
        while (prev.containsKey(step)) {
            step = prev.get(step);
            path.addFirst(step);
        }
        return path;
    }
}


