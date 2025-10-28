package com.acu.connectedcircles;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * JavaFX app for the Connected Circles problem.
 * Each circle is a vertex. Two vertices are adjacent if their circles overlap.
 * DFS is used to check connectivity (optionally per color group).
 */
public class App extends Application {

    // ACU color palette
    private static final Color ACU_PURPLE = Color.web("#3C1053");
    private static final Color ACU_RED = Color.web("#F2120C");
    private static final Color ACU_BLACK = Color.web("#000000");
    private static final Color ACU_WHITE = Color.web("#FFFFFF");
    private static final Color ACU_WARM_STONE = Color.web("#918B83");
    private static final Color ACU_DEEP_CHARCOAL = Color.web("#302C2A");
    private static final Color ACU_SOFT_IVORY = Color.web("#F2EFEB");

    private final Pane canvas = new Pane();
    private final ComboBox<Integer> radiusBox = new ComboBox<>();
    private final ComboBox<String> colorBox = new ComboBox<>();
    private final CheckBox sameColorCheck = new CheckBox("Same Color Check");

    private final List<CircleItem> items = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F2EFEB;"); // Soft Ivory background

        canvas.setPrefSize(800, 600);
        canvas.setBackground(new Background(new BackgroundFill(ACU_SOFT_IVORY, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setCenter(canvas);

        // Controls
        HBox controls = new HBox(10);
        controls.setPadding(new Insets(8));
        controls.setBackground(new Background(new BackgroundFill(ACU_WARM_STONE, CornerRadii.EMPTY, Insets.EMPTY)));

        radiusBox.getItems().addAll(10,20,30,40,50,60,70,80,90,100);
        radiusBox.setValue(20);

        colorBox.getItems().addAll("Purple","Red","Green","Blue","Yellow");
        colorBox.setValue("Purple");

        sameColorCheck.setSelected(true);

        Button resetBtn = new Button("Reset");
        Button randomBtn = new Button("Randomize");
        Button checkBtn = new Button("Check");
        Button algoBtn = new Button("Algorithm");
        Button helpBtn = new Button("Help");
        Button aboutBtn = new Button("About");

        controls.getChildren().addAll(new Label("Radius:"), radiusBox,
                new Label("Color:"), colorBox, sameColorCheck,
                resetBtn, randomBtn, checkBtn, algoBtn, helpBtn, aboutBtn);
        root.setTop(controls);

        // Mouse interactions
        canvas.setOnMouseClicked(e -> {
            if (isCovered(e.getX(), e.getY())) return; // do not place on an existing circle
            CircleItem ci = new CircleItem(e.getX(), e.getY(), radiusBox.getValue(), selectedColor());
            addCircle(ci);
        });

        // Buttons
        resetBtn.setOnAction(e -> reset());
        randomBtn.setOnAction(e -> randomize());
        checkBtn.setOnAction(e -> {
            updateFillStates();
            boolean connected = isOverallConnected();
            String msg = connected ? "Circles are connected." : "Circles are NOT connected.";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
            alert.setHeaderText(null);
            alert.setTitle("Connectivity Result");
            alert.showAndWait();
        });
        algoBtn.setOnAction(e -> showAlgorithm());
        helpBtn.setOnAction(e -> showHelp());
        aboutBtn.setOnAction(e -> showAbout());

        Scene scene = new Scene(root);
        stage.setTitle("ConnectedCircles");
        stage.setScene(scene);
        stage.show();
    }

    private void addCircle(CircleItem item) {
        Circle view = createCircleView(item);
        item.setView(view);
        items.add(item);
        canvas.getChildren().add(view);
        updateFillStates();
    }

    private Circle createCircleView(CircleItem item) {
        Circle c = new Circle(item.getX(), item.getY(), item.getRadius());
        c.setStroke(ACU_DEEP_CHARCOAL);
        c.setFill(Color.TRANSPARENT);
        c.setOnMousePressed(evt -> {
            item.setDragOffset(new Point2D(evt.getX() - c.getCenterX(), evt.getY() - c.getCenterY()));
            evt.consume();
        });
        c.setOnMouseDragged(evt -> {
            Point2D off = item.getDragOffset();
            double nx = evt.getX() - (off == null ? 0 : off.getX());
            double ny = evt.getY() - (off == null ? 0 : off.getY());
            item.setX(nx);
            item.setY(ny);
            c.setCenterX(nx);
            c.setCenterY(ny);
            updateFillStates();
            evt.consume();
        });
        c.setOnMouseClicked(evt -> {
            // allow recolor/radius change on selected circle via current selectors
            item.setColor(selectedColor());
            item.setRadius(radiusBox.getValue());
            c.setRadius(item.getRadius());
            updateFillStates();
            evt.consume();
        });
        return c;
    }

    private boolean isCovered(double x, double y) {
        for (CircleItem it : items) {
            double dx = x - it.getX();
            double dy = y - it.getY();
            if (Math.hypot(dx, dy) <= it.getRadius()) return true;
        }
        return false;
    }

    private void reset() {
        items.clear();
        canvas.getChildren().clear();
    }

    private void randomize() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int radius = List.of(10,20,30,40,50,60,70,80,90,100).get(r.nextInt(10));
            double x = 40 + r.nextDouble() * Math.max(0, canvas.getWidth() - 80);
            double y = 40 + r.nextDouble() * Math.max(0, canvas.getHeight() - 80);
            Color color = randomColor(r);
            addCircle(new CircleItem(x, y, radius, color));
        }
        updateFillStates();
    }

    private Color selectedColor() {
        return switch (colorBox.getValue()) {
            case "Red" -> ACU_RED;
            case "Green" -> Color.GREEN;
            case "Blue" -> Color.BLUE;
            case "Yellow" -> Color.YELLOW;
            default -> ACU_PURPLE;
        };
    }

    private Color randomColor(Random r) {
        return switch (r.nextInt(5)) {
            case 0 -> ACU_PURPLE;
            case 1 -> ACU_RED;
            case 2 -> Color.GREEN;
            case 3 -> Color.BLUE;
            default -> Color.YELLOW;
        };
    }

    private void updateFillStates() {
        if (items.isEmpty()) return;

        if (sameColorCheck.isSelected()) {
            Map<Color, List<CircleItem>> groups = items.stream().collect(Collectors.groupingBy(CircleItem::getColor));
            for (Map.Entry<Color, List<CircleItem>> e : groups.entrySet()) {
                boolean connected = GraphUtils.isConnected(e.getValue());
                for (CircleItem item : e.getValue()) {
                    applyFill(item, connected);
                }
            }
        } else {
            boolean connected = GraphUtils.isConnected(items);
            for (CircleItem item : items) applyFill(item, connected);
        }
    }

    // Determines overall connectivity for messaging: with Same Color on, all color groups must be connected
    private boolean isOverallConnected() {
        if (items.isEmpty()) return true;
        if (sameColorCheck.isSelected()) {
            Map<Color, List<CircleItem>> groups = items.stream().collect(Collectors.groupingBy(CircleItem::getColor));
            for (List<CircleItem> group : groups.values()) {
                if (!GraphUtils.isConnected(group)) return false;
            }
            return true;
        }
        return GraphUtils.isConnected(items);
    }

    private void applyFill(CircleItem item, boolean filled) {
        Circle v = item.getView();
        if (v == null) return;
        v.setStroke(ACU_DEEP_CHARCOAL);
        v.setFill(filled ? item.getColor() : Color.TRANSPARENT);
    }

    private void showAlgorithm() {
        String text = "1) Treat each circle as a vertex.\n" +
                "2) Connect two vertices when their circles overlap/touch.\n" +
                "3) Run DFS from any vertex.\n" +
                "4) If all vertices are visited, the set is connected.";
        new Alert(Alert.AlertType.INFORMATION, text).showAndWait();
    }

    private void showHelp() {
        String text = "Click to create a circle. Drag to move. Use controls to set\n" +
                "radius and color. Randomize adds 10 circles. Check runs DFS\n" +
                "to determine connectivity.";
        new Alert(Alert.AlertType.INFORMATION, text).showAndWait();
    }

    private void showAbout() {
        String text = "Connected Circles – ACU color scheme. Educational demo of\n" +
                "graph modeling and DFS.";
        new Alert(Alert.AlertType.INFORMATION, text).showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


