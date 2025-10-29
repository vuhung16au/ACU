package com.acu.javafx.augraph;

import java.util.*;

/**
 * Simple weighted undirected graph for educational purposes.
 */
public class Graph {
    public static class Edge {
        public final String from;
        public final String to;
        public final double weight;
        public Edge(String from, String to, double weight) {
            this.from = from; this.to = to; this.weight = weight;
        }
    }

    public static class CityNode {
        public final String name;
        public final double x;
        public final double y;
        public CityNode(String name, double x, double y) {
            this.name = name; this.x = x; this.y = y;
        }
    }

    private final Map<String, CityNode> nameToCity = new LinkedHashMap<>();
    private final Map<String, List<Edge>> adj = new LinkedHashMap<>();

    public void addCity(CityNode node) {
        nameToCity.put(node.name, node);
        adj.computeIfAbsent(node.name, k -> new ArrayList<>());
    }

    public void addUndirectedEdge(String a, String b, double weight) {
        Edge ab = new Edge(a, b, weight);
        Edge ba = new Edge(b, a, weight);
        adj.computeIfAbsent(a, k -> new ArrayList<>()).add(ab);
        adj.computeIfAbsent(b, k -> new ArrayList<>()).add(ba);
    }

    public Set<String> getCities() { return nameToCity.keySet(); }
    public CityNode getCity(String name) { return nameToCity.get(name); }
    public List<Edge> neighbors(String city) { return adj.getOrDefault(city, List.of()); }
}


