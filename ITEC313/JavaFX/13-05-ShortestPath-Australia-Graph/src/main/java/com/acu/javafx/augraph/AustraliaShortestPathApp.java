package com.acu.javafx.augraph;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * JavaFX entry point for the Australia shortest path demo.
 * The UI shows a background map, cities as nodes, weighted edges, and allows
 * computing shortest paths using Dijkstra's algorithm.
 */
public class AustraliaShortestPathApp extends Application {

    private GraphView graphView;

    // ACU colour palette used in the UI
    private static final String COLOR_PURPLE = "#3C1053";
    private static final String COLOR_RED = "#F2120C";
    private static final String COLOR_SOFT_IVORY = "#F2EFEB";
    private static final String COLOR_CHARCOAL = "#302C2A";

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        // Background map (use provided absolute image in the repository root)
        ImageView background = new ImageView();
        Path mapPath = Path.of(System.getProperty("user.dir"), "australia-map-1400px.jpg");
        if (Files.exists(mapPath)) {
            background.setImage(new Image(Files.newInputStream(mapPath)));
        } else {
            // Fallback: try to load from resources if available
            InputStream in = getClass().getResourceAsStream("/australia-map-1400px.jpg");
            if (in != null) {
                background.setImage(new Image(in));
            }
        }
        background.setPreserveRatio(true);
        background.setFitWidth(1100);

        graphView = new GraphView();
        graphView.setPickOnBounds(false);
        graphView.setPrefSize(1100, 800);
        graphView.loadGraphFromResource("/australia-graph.json");
        // Shift drawing up so nodes near the south coast aren't hidden by controls
        graphView.setTranslateY(-140);

        BorderPane mapLayer = new BorderPane(background);
        mapLayer.setCenter(graphView);
        root.setCenter(mapLayer);

        HBox controls = new HBox(10);
        controls.setPadding(new Insets(8));
        controls.setStyle("-fx-background-color:" + COLOR_SOFT_IVORY + "; -fx-border-color:" + COLOR_CHARCOAL + ";");

        ComboBox<String> startCity = new ComboBox<>();
        ComboBox<String> endCity = new ComboBox<>();
        startCity.getItems().addAll(graphView.getCityNames());
        endCity.getItems().addAll(graphView.getCityNames());
        startCity.setPromptText("Start City");
        endCity.setPromptText("End City");

        Button find = mkButton("Find Path");
        find.setOnAction(e -> {
            String s = startCity.getValue();
            String t = endCity.getValue();
            if (s == null || t == null) {
                alert("Please choose both start and end cities.");
                return;
            }
            List<String> path = Dijkstra.shortestPath(graphView.getGraph(), s, t);
            graphView.showPath(path);
        });

        Button demo = mkButton("Demo");
        demo.setOnAction(e -> {
            List<String> path = Dijkstra.shortestPath(graphView.getGraph(), "Sydney", "Darwin");
            startCity.setValue("Sydney");
            endCity.setValue("Darwin");
            graphView.showPath(path);
        });

        Button reset = mkButton("Reset");
        reset.setOnAction(e -> graphView.reset());

        Button help = mkButton("Help");
        help.setOnAction(e -> alert(
                "1) Pick Start and End from the lists.\n" +
                "2) Click 'Find Path' to compute the shortest route and total distance (in kilometers).\n" +
                "3) 'Demo' shows Sydney → Adelaide. 'Reset' restores the base map.\n" +
                "Tips: You can run the algorithm multiple times with different cities; the red path overlays the grey network."));

        Button about = mkButton("About");
        about.setOnAction(e -> alert("Australia Shortest Path — Dijkstra visualisation."));

        Button algorithm = mkButton("Algorithm");
        algorithm.setOnAction(e -> alert(
                "Using Dijkstra (greedy with a min‑priority queue).\n" +
                "Initialise dist[source]=0, others=∞; repeatedly relax edges of the closest unvisited city.\n" +
                "Assumes non‑negative weights. Also see: A* (heuristic), Bellman‑Ford (handles negatives), Floyd–Warshall (all‑pairs)."));

        Button strategy = mkButton("Strategy");
        strategy.setOnAction(e -> alert(
                "Strategy: model each city as a node, connect cities with undirected edges weighted by road distance (km).\n" +
                "Choose two cities and run Dijkstra to minimise the sum of edge distances.\n" +
                "Edge set focuses on realistic inter‑city links; you can extend the JSON to add more roads."));

        controls.getChildren().addAll(new Label("Start:"), startCity, new Label("End:"), endCity,
                find, demo, reset, help, algorithm, about, strategy);
        root.setBottom(controls);

        Scene scene = new Scene(root, 1120, 860);
        stage.setTitle("Australia Shortest Path (Dijkstra)");
        stage.setScene(scene);
        stage.show();
    }

    private Button mkButton(String text) {
        Button b = new Button(text);
        b.setStyle("-fx-background-color:" + COLOR_PURPLE + "; -fx-text-fill: white; -fx-font-weight: bold;");
        return b;
    }

    private void alert(String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        a.setHeaderText(null);
        a.setTitle("Info");
        a.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


