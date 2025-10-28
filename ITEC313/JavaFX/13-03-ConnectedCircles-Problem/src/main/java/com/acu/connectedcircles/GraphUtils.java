package com.acu.connectedcircles;

import java.util.*;

/** Utility methods for building the overlap graph and running DFS. */
public final class GraphUtils {
    private GraphUtils() {}

    /** Returns true if all items are connected via overlaps. */
    public static boolean isConnected(List<CircleItem> items) {
        if (items.isEmpty()) return true;

        Map<Integer, List<Integer>> g = buildGraph(items);
        Set<Integer> visited = new HashSet<>();
        dfs(0, g, visited);
        return visited.size() == items.size();
    }

    /** Build adjacency list where an edge exists if circles overlap. */
    static Map<Integer, List<Integer>> buildGraph(List<CircleItem> items) {
        Map<Integer, List<Integer>> g = new HashMap<>();
        int n = items.size();
        for (int i = 0; i < n; i++) g.put(i, new ArrayList<>());
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (items.get(i).overlaps(items.get(j))) {
                    g.get(i).add(j);
                    g.get(j).add(i);
                }
            }
        }
        return g;
    }

    private static void dfs(int u, Map<Integer, List<Integer>> g, Set<Integer> vis) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(u);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!vis.add(v)) continue;
            for (int w : g.get(v)) {
                if (!vis.contains(w)) stack.push(w);
            }
        }
    }
}


