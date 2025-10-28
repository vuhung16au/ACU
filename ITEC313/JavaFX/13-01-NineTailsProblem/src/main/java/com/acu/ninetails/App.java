package com.acu.ninetails;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * JavaFX app for the Nine Tails problem.
 * Users can play by clicking a head coin to flip it and its neighbors.
 * A Solve button reveals the shortest sequence to reach the all-tails state
 * using a BFS over the state graph (implemented in {@link NineTailsModel}).
 */
public class App extends Application {

    // ACU color palette
    private static final Color ACU_PURPLE = Color.web("#3C1053");
    private static final Color ACU_RED = Color.web("#F2120C");
    private static final Color ACU_SOFT_IVORY = Color.web("#F2EFEB");
    private static final Color ACU_CHARCOAL = Color.web("#302C2A");

    private final Random random = new Random();

    private int size = 3; // board size (NxN)
    private int state;    // current board as bitset (0 = H, 1 = T)
    private int initialState;

    private GridPane grid;
    private TextArea output;

    private NineTailsModel model;

    @Override
    public void start(Stage stage) {
        this.model = new NineTailsModel(size);
        this.state = randomState();
        this.initialState = state;

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));
        root.setStyle("-fx-background-color: " + toRgb(ACU_SOFT_IVORY) + ";");

        grid = buildGrid();
        output = new TextArea();
        output.setEditable(false);
        output.setPrefRowCount(6);
        output.setWrapText(true);
        output.setStyle("-fx-control-inner-background: " + toRgb(Color.WHITE) + 
                "; -fx-font-family: 'Menlo, Consolas, monospace';");

        HBox controls = buildControls();

        Label title = new Label("Nine Tails - Shortest Path");
        title.setTextFill(ACU_PURPLE);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        BorderPane top = new BorderPane();
        top.setLeft(title);
        top.setPadding(new Insets(0, 0, 8, 0));

        root.setTop(top);
        root.setCenter(grid);
        root.setBottom(new BorderPane(output, null, null, controls, null));

        Scene scene = new Scene(root, 560, 640);
        stage.setTitle("Nine Tails");
        stage.setScene(scene);
        stage.show();
        refreshGrid();
    }

    private HBox buildControls() {
        Button solve = button("Solve", ACU_PURPLE);
        solve.setOnAction(e -> solve());

        Button reset = button("Reset", ACU_CHARCOAL);
        reset.setOnAction(e -> {
            state = initialState;
            output.clear();
            refreshGrid();
        });

        Button newGame = button("New", ACU_RED);
        newGame.setOnAction(e -> {
            state = randomState();
            initialState = state;
            output.clear();
            refreshGrid();
        });

        Button sizeBtn = button("Size", ACU_CHARCOAL);
        sizeBtn.setOnAction(e -> changeSize());

        Button help = button("Help", ACU_PURPLE);
        help.setOnAction(e -> showHelp());

        Button algorithm = button("Algorithm", ACU_CHARCOAL);
        algorithm.setOnAction(e -> showAlgorithm());

        Button strategy = button("Strategy", ACU_CHARCOAL);
        strategy.setOnAction(e -> showStrategy());

        HBox box = new HBox(10, solve, reset, newGame, sizeBtn, help, algorithm, strategy);
        box.setAlignment(Pos.CENTER_RIGHT);
        box.setPadding(new Insets(8, 0, 0, 0));
        return box;
    }

    private Button button(String text, Color color) {
        Button b = new Button(text);
        b.setStyle("-fx-background-color: " + toRgb(color) + 
                "; -fx-text-fill: white; -fx-font-weight: bold;");
        return b;
    }

    private GridPane buildGrid() {
        GridPane gp = new GridPane();
        gp.setHgap(6);
        gp.setVgap(6);
        gp.setAlignment(Pos.CENTER);
        gp.setPadding(new Insets(16));
        return gp;
    }

    private void refreshGrid() {
        grid.getChildren().clear();
        int cellSize = Math.max(40, 380 / size);
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                int idx = r * size + c;
                boolean isTail = ((state >> idx) & 1) == 1;
                Rectangle rect = new Rectangle(cellSize, cellSize);
                rect.setArcWidth(10);
                rect.setArcHeight(10);
                rect.setStroke(ACU_CHARCOAL);
                rect.setStrokeWidth(2);
                rect.setFill(isTail ? ACU_PURPLE : Color.WHITE);

                Label label = new Label(isTail ? "T" : "H");
                label.setTextFill(isTail ? Color.WHITE : ACU_CHARCOAL);
                label.setStyle("-fx-font-size: " + Math.max(18, cellSize/3) + "px; -fx-font-weight: bold;");

                StackPane cell = new StackPane(rect, label);
                cell.setOnMouseClicked(ev -> {
                    if (ev.getButton() == MouseButton.PRIMARY) {
                        state = model.flipAny(state, idx); // flip coin and neighbors
                        refreshGrid();
                        if (state == model.getTargetState()) {
                            output.appendText("Solved!\n");
                        }
                    }
                });
                gpAdd(cell, r, c);
            }
        }
    }

    private void gpAdd(javafx.scene.Node node, int r, int c) {
        grid.add(node, c, r);
    }

    private void changeSize() {
        try {
            int newSize = SizeDialog.ask(size);
            if (newSize == size) return;
            this.size = newSize;
            this.model = new NineTailsModel(size);
            this.state = randomState();
            this.initialState = state;
            refreshGrid();
            output.clear();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    private int randomState() {
        int bits = size * size;
        int s = 0;
        for (int i = 0; i < bits; i++) {
            s |= (random.nextBoolean() ? 1 : 0) << i; // random tails/heads
        }
        return s;
    }

    private void solve() {
        List<Integer> path = model.shortestPath(state);
        if (path.isEmpty()) {
            output.appendText("No solution found.\n");
            return;
        }
        // Print textual board states
        output.appendText("The steps to flip the coins are\n");
        for (int s : path) {
            output.appendText(NineTailsModel.toBoardString(s, size));
            output.appendText("\n");
        }
        // Animate on board
        Timeline tl = new Timeline();
        List<KeyFrame> frames = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            int s = path.get(i);
            frames.add(new KeyFrame(Duration.millis(400L * (i + 1)), e -> {
                state = s;
                refreshGrid();
            }));
        }
        tl.getKeyFrames().addAll(frames);
        tl.play();
    }

    private void showHelp() {
        String msg = "How to Play Nine Tails\n\n" +
                "- The grid shows coins: H = Head (white), T = Tail (purple).\n" +
                "- A move flips a coin and its four neighbors (up, down, left, right).\n" +
                "- Click any coin to perform that flip.\n" +
                "- Goal: make all coins Tail.\n\n" +
                "Buttons:\n" +
                "- Solve: show a shortest sequence from the current board to the goal.\n" +
                "- Reset: restore the starting board.\n" +
                "- New: generate a new random board.\n" +
                "- Size: change board dimension (2–5).";
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setHeaderText("Nine Tails – Help");
        a.setTitle("Help");
        a.showAndWait();
    }

    private void showAlgorithm() {
        String msg = "BFS Algorithm (shortest path)\n\n" +
                "1) Encode an N×N board as an integer bitset (0=H,1=T).\n" +
                "2) Precompute a flip-mask per cell covering itself and its 4-neighbors.\n" +
                "3) Let target be all tails (all 1s).\n" +
                "4) Run BFS starting from target. For each popped state v,\n" +
                "   for every index i: u = v XOR mask[i]. If coin i is head in u,\n" +
                "   record parent[u]=v and enqueue.\n" +
                "5) The path from any start s to target is obtained by following\n" +
                "   parent links s → ... → target. This is minimal by BFS.\n";
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setHeaderText("Nine Tails – Algorithm");
        a.setTitle("Algorithm");
        a.showAndWait();
    }

    private void showStrategy() {
        String msg = "Solving Strategy (intuitive)\n\n" +
                "- Click coins to reduce the number of heads.\n" +
                "- Prefer flipping where many neighbors are heads to convert several at once.\n" +
                "- If stuck, press Solve to see a guaranteed shortest sequence, then\n" +
                "  try to reproduce/understand the pattern.\n" +
                "- For learning: compare your moves to the BFS solution in the log.\n";
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setHeaderText("Nine Tails – Strategy");
        a.setTitle("Strategy");
        a.showAndWait();
    }

    private static String toRgb(Color c) {
        return String.format("#%02X%02X%02X",
                (int) Math.round(c.getRed() * 255),
                (int) Math.round(c.getGreen() * 255),
                (int) Math.round(c.getBlue() * 255));
    }

    public static void main(String[] args) {
        launch(args);
    }
}


