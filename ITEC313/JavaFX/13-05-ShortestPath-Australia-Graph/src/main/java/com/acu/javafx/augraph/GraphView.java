package com.acu.javafx.augraph;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;

import java.io.InputStream;
import java.util.*;

/**
 * Renders the graph and provides utilities to highlight a path.
 */
public class GraphView extends Pane {
    private final Graph graph = new Graph();
    private final Map<String, Circle> cityDots = new HashMap<>();
    private final List<Group> allEdges = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    private static final Color COLOR_NODE = Color.web("#3C1053");
    private static final Color COLOR_EDGE = Color.web("#302C2A");
    private static final Color COLOR_PATH = Color.web("#F2120C");

    public Graph getGraph() { return graph; }

    public List<String> getCityNames() {
        return new ArrayList<>(graph.getCities());
    }

    public void loadGraphFromResource(String resource) {
        try (InputStream in = getClass().getResourceAsStream(resource)) {
            JsonNode root = mapper.readTree(in);
            for (JsonNode n : root.get("nodes")) {
                String name = n.get("name").asText();
                double x = n.get("x").asDouble();
                double y = n.get("y").asDouble();
                graph.addCity(new Graph.CityNode(name, x, y));
            }
            for (JsonNode e : root.get("edges")) {
                String a = e.get("from").asText();
                String b = e.get("to").asText();
                double w = e.get("distance").asDouble();
                graph.addUndirectedEdge(a, b, w);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        draw();
    }

    public void reset() { getChildren().setAll(render()); }

    private void draw() { getChildren().add(render()); }

    private Group render() {
        Group g = new Group();
        allEdges.clear();
        cityDots.clear();

        // Draw edges with distance labels
        for (String a : graph.getCities()) {
            for (Graph.Edge e : graph.neighbors(a)) {
                if (a.compareTo(e.to) < 0) { // draw once per undirected edge
                    Group eg = drawEdge(graph.getCity(a), graph.getCity(e.to), e.weight, COLOR_EDGE, 1.5);
                    allEdges.add(eg);
                    g.getChildren().add(eg);
                }
            }
        }

        // Draw nodes
        for (String name : graph.getCities()) {
            Graph.CityNode c = graph.getCity(name);
            Circle dot = new Circle(c.x, c.y, 5, COLOR_NODE);
            Text label = new Text(c.x + 8, c.y - 8, name);
            label.setFill(COLOR_NODE);
            label.setFont(Font.font(13));
            cityDots.put(name, dot);
            g.getChildren().addAll(dot, label);
        }
        return g;
    }

    private Group drawEdge(Graph.CityNode a, Graph.CityNode b, double w, Color color, double width) {
        Line line = new Line(a.x, a.y, b.x, b.y);
        line.setStroke(color);
        line.setStrokeWidth(width);
        Text weight = new Text((a.x + b.x) / 2, (a.y + b.y) / 2, String.format("%.0f", w));
        weight.setFill(color);
        weight.setFont(Font.font(11));
        return new Group(line, weight);
    }

    public void showPath(List<String> path) {
        reset();
        if (path == null || path.size() < 2) return;
        // overlay the red path lines
        List<Node> overlay = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            Graph.CityNode a = graph.getCity(path.get(i));
            Graph.CityNode b = graph.getCity(path.get(i + 1));
            overlay.add(drawEdge(a, b, distanceBetween(a, b), COLOR_PATH, 3.0));
        }
        getChildren().addAll(overlay);
    }

    private double distanceBetween(Graph.CityNode a, Graph.CityNode b) {
        // Euclidean pixel distance is not what we want in labels for the path overlay; keep neutral.
        return 0.0;
    }
}


