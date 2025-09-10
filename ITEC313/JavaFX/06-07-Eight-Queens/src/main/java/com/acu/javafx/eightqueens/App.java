package com.acu.javafx.eightqueens;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX UI for visualizing the N-Queens problem with simple animation.
 * The solver emits steps whenever it places/removes a queen; these steps
 * are played back on a timeline to illustrate the backtracking process.
 */
public class App extends Application {
    // Base window reference and padding to accommodate controls/status
    private Stage primaryStage;
    private static final double H_PADDING = 40;  // left+right margins
    private static final double V_PADDING = 140; // top+bottom areas

    private final GridPane boardGrid = new GridPane();
    private final Label statusLabel = new Label("Ready");
    private final Spinner<Integer> sizeSpinner = new Spinner<>();
    private final Button solveButton = new Button("Solve Eight Queens");

    // Timeline for animating solver steps
    private Timeline timeline;

    // State rendered on the board
    private int n = 8;
    private int[] queens; // queens[row] = col or -1 if empty
    private final Rectangle[][] cells = new Rectangle[16][16];
    private final Text[][] marks = new Text[16][16];

    @Override
    public void start(Stage stage) {
        stage.setTitle("Eight Queens - N-Queens Visualizer");
        primaryStage = stage;

        // Top controls: N selector and Solve button
        sizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 16, 8));
        sizeSpinner.setEditable(true);
        solveButton.setOnAction(e -> runSolve());
        HBox controls = new HBox(10, new Label("Board size (N):"), sizeSpinner, solveButton);
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setPadding(new Insets(10));

        // Center board
        boardGrid.setGridLinesVisible(true);
        boardGrid.setPadding(new Insets(10));
        boardGrid.setHgap(0);
        boardGrid.setVgap(0);

        BorderPane root = new BorderPane();
        root.setTop(controls);
        root.setCenter(boardGrid);
        statusLabel.setPadding(new Insets(6, 10, 10, 10));
        root.setBottom(statusLabel);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Initial draw
        n = sizeSpinner.getValue();
        drawEmptyBoard(n);
    }

    private void runSolve() {
        // Stop previous animation if any
        if (timeline != null) {
            timeline.stop();
        }

        n = sizeSpinner.getValue();
        drawEmptyBoard(n);
        statusLabel.setText("Solving for N=" + n + " ...");

        queens = new int[n];
        for (int i = 0; i < n; i++) queens[i] = -1;

        // Collect steps via solver
        List<NQueenSolver.Step> steps = new ArrayList<>();
        NQueenSolver.solveWithSteps(n, steps);

        // Build a simple timeline to play steps (place/remove/mark attacks)
        timeline = new Timeline();
        timeline.setCycleCount(1);

        double delayMs = 250; // per step
        int[] currentQueens = new int[n];
        for (int i = 0; i < n; i++) currentQueens[i] = -1;

        for (int i = 0; i < steps.size(); i++) {
            final NQueenSolver.Step step = steps.get(i);
            Duration at = Duration.millis(i * delayMs);
            timeline.getKeyFrames().add(new KeyFrame(at, e -> applyStep(step, currentQueens)));
        }

        // Final status at the end
        if (!steps.isEmpty()) {
            Duration endAt = Duration.millis(steps.size() * delayMs + 1);
            timeline.getKeyFrames().add(new KeyFrame(endAt, e -> statusLabel.setText("Done")));
        }
        timeline.playFromStart();
    }

    private void applyStep(NQueenSolver.Step step, int[] currentQueens) {
        switch (step.type) {
            case PLACE -> {
                currentQueens[step.row] = step.col;
                drawState(currentQueens);
                statusLabel.setText("Placed ♕ at (" + step.row + "," + step.col + ")");
                // Stop animation immediately when a full solution is reached
                if (isSolved(currentQueens) && timeline != null) {
                    timeline.stop();
                    statusLabel.setText("Solution found");
                }
            }
            case REMOVE -> {
                currentQueens[step.row] = -1;
                drawState(currentQueens);
                statusLabel.setText("Backtrack from (" + step.row + "," + step.col + ")");
            }
            case MARK -> {
                // Marks are recomputed in drawState, so we only update status here
                statusLabel.setText("Attacks updated");
            }
        }
    }

    // Returns true if every row has a queen placed
    private boolean isSolved(int[] currentQueens) {
        for (int q : currentQueens) if (q < 0) return false;
        return true;
    }

    private void drawEmptyBoard(int size) {
        boardGrid.getChildren().clear();
        // Choose a cell size based on N, then resize the window to fit the board
        double cellSize = Math.max(24, Math.min(64, 640.0 / size));
        if (primaryStage != null) {
            primaryStage.setWidth(cellSize * size + H_PADDING);
            primaryStage.setHeight(cellSize * size + V_PADDING);
        }
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Rectangle rect = new Rectangle(cellSize, cellSize);
                rect.setFill(((r + c) % 2 == 0) ? Color.WHITE : Color.web("#e6f0ff"));
                rect.setStroke(Color.GRAY);
                cells[r][c] = rect;

                Text t = new Text("");
                t.setFont(Font.font(cellSize * 0.6));
                marks[r][c] = t;

                BorderPane cell = new BorderPane();
                cell.setCenter(t);
                cell.setPrefSize(cellSize, cellSize);
                cell.setMinSize(cellSize, cellSize);
                cell.setMaxSize(cellSize, cellSize);
                cell.setBackground(null);

                boardGrid.add(rect, c, r);
                boardGrid.add(cell, c, r);
            }
        }
    }

    private void drawState(int[] currentQueens) {
        int size = currentQueens.length;
        // Clear marks
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                marks[r][c].setText("");
                marks[r][c].setFill(Color.DARKGRAY);
            }
        }

        // Draw queens
        for (int r = 0; r < size; r++) {
            int col = currentQueens[r];
            if (col >= 0) {
                marks[r][col].setText("♕");
                marks[r][col].setFill(Color.web("#333"));
            }
        }

        // Draw attack dots for current queen positions
        for (int r = 0; r < size; r++) {
            int col = currentQueens[r];
            if (col >= 0) {
                for (int rr = 0; rr < size; rr++) {
                    for (int cc = 0; cc < size; cc++) {
                        if (rr == r && cc == col) continue;
                        if (NQueenSolver.attacks(r, col, rr, cc)) {
                            if (marks[rr][cc].getText().isEmpty()) {
                                marks[rr][cc].setText("•");
                                marks[rr][cc].setFill(Color.web("#888"));
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


