package com.acu.javafx.hanoi;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JavaFX visualization for the non-recursive Tower of Hanoi solver.
 *
 * Visual styling inspired by "Tower of Hanoi Animation.html":
 * - Pegs/bases: chocolate
 * - Disc palette: crimson, cornflowerblue, beige, purple, aqua, darkorange, goldenrod, #37826C
 */
public class HanoiTowerSolverApp extends Application {

    private static final double CANVAS_WIDTH = 800;
    private static final double CANVAS_HEIGHT = 450;

    // Geometry for pegs and base (roughly matching the referenced HTML)
    private static final double BASE_TOP_Y = 320;
    private static final double BASE_HEIGHT = 6;
    private static final double BASE_WIDTH = 150;
    private static final double PEG_TOP_Y = 190;
    private static final double PEG_HEIGHT = 130;
    private static final double PEG_WIDTH = 3;

    private static final double DISC_HEIGHT = 20;
    private static final int MAX_DISCS = 8;

    private final Color pegColor = Color.CHOCOLATE;
    private final Color[] discColors = new Color[] {
            Color.CRIMSON,
            Color.CORNFLOWERBLUE,
            Color.BEIGE,
            Color.PURPLE,
            Color.AQUA,
            Color.DARKORANGE,
            Color.GOLDENROD,
            Color.web("#37826C")
    };

    private Pane board;
    private Spinner<Integer> discSpinner;
    private Button startButton;
    private Button resetButton;
    private Slider speedSlider;
    private Label statusLabel;

    private final Map<Character, Deque<Rectangle>> pegToDiscs = new HashMap<>();
    private final Map<Rectangle, Double> discBaseX = new HashMap<>();
    private final Map<Rectangle, Double> discBaseY = new HashMap<>();

    private double pegAX;
    private double pegBX;
    private double pegCX;

    private SequentialTransition sequence;
    private boolean isRunning = false;

    @Override
    public void start(Stage stage) {
        board = new Pane();
        board.setPrefSize(CANVAS_WIDTH, CANVAS_HEIGHT);

        BorderPane root = new BorderPane();
        root.setCenter(board);
        root.setTop(createControls());
        root.setBottom(createStatusBar());

        Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT);
        stage.setTitle("Tower of Hanoi (Stack-based)");
        stage.setScene(scene);
        stage.show();

        drawBoard();
        resetScene();
    }

    private HBox createControls() {
        discSpinner = new Spinner<>();
        discSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, MAX_DISCS, 3));
        discSpinner.setEditable(false);

        startButton = new Button("Start");
        resetButton = new Button("Reset");

        speedSlider = new Slider(0.25, 2.0, 1.0);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
        speedSlider.setMajorTickUnit(0.25);
        speedSlider.setMinorTickCount(0);

        startButton.setOnAction(e -> startAnimation());
        resetButton.setOnAction(e -> resetScene());

        HBox controls = new HBox(10,
                new Label("Discs:"), discSpinner,
                startButton, resetButton,
                new Label("Speed:"), speedSlider
        );
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER);
        return controls;
    }

    private HBox createStatusBar() {
        statusLabel = new Label("");
        HBox box = new HBox(statusLabel);
        box.setPadding(new Insets(6, 10, 10, 10));
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void drawBoard() {
        board.getChildren().clear();

        double left = 100;
        double gap = 200;
        pegAX = left + BASE_WIDTH / 2;
        pegBX = left + gap + BASE_WIDTH / 2;
        pegCX = left + 2 * gap + BASE_WIDTH / 2;

        // Bases
        Rectangle baseA = new Rectangle(left, BASE_TOP_Y, BASE_WIDTH, BASE_HEIGHT);
        Rectangle baseB = new Rectangle(left + gap, BASE_TOP_Y, BASE_WIDTH, BASE_HEIGHT);
        Rectangle baseC = new Rectangle(left + 2 * gap, BASE_TOP_Y, BASE_WIDTH, BASE_HEIGHT);
        baseA.setFill(pegColor);
        baseB.setFill(pegColor);
        baseC.setFill(pegColor);

        // Pegs
        Line pegA = new Line(pegAX, PEG_TOP_Y, pegAX, PEG_TOP_Y + PEG_HEIGHT);
        Line pegB = new Line(pegBX, PEG_TOP_Y, pegBX, PEG_TOP_Y + PEG_HEIGHT);
        Line pegC = new Line(pegCX, PEG_TOP_Y, pegCX, PEG_TOP_Y + PEG_HEIGHT);
        pegA.setStroke(pegColor);
        pegB.setStroke(pegColor);
        pegC.setStroke(pegColor);
        pegA.setStrokeWidth(PEG_WIDTH);
        pegB.setStrokeWidth(PEG_WIDTH);
        pegC.setStrokeWidth(PEG_WIDTH);

        board.getChildren().addAll(baseA, baseB, baseC, pegA, pegB, pegC);
    }

    private void resetScene() {
        if (sequence != null) {
            sequence.stop();
        }
        isRunning = false;
        startButton.setDisable(false);
        discSpinner.setDisable(false);

        // Clear any discs
        board.getChildren().removeIf(n -> n instanceof Rectangle && n != null && ((Rectangle) n).getUserData() != null);
        pegToDiscs.clear();
        discBaseX.clear();
        discBaseY.clear();

        pegToDiscs.put('A', new ArrayDeque<>());
        pegToDiscs.put('B', new ArrayDeque<>());
        pegToDiscs.put('C', new ArrayDeque<>());

        int n = discSpinner.getValue();

        // Layout discs on peg A (largest at bottom)
        for (int i = n; i >= 1; i--) {
            Rectangle disc = createDisc(i, n);
            placeDiscOnPeg(disc, 'A');
        }

        statusLabel.setText("The number of moves for " + n + " discs is " + ((int) (Math.pow(2, n) - 1)));
    }

    private Rectangle createDisc(int indexFromTop, int totalDiscs) {
        // indexFromTop: 1..totalDiscs where totalDiscs is largest (bottom)
        int paletteIndex = Math.min(totalDiscs - indexFromTop, discColors.length - 1);
        double width = 150 - (8 - (totalDiscs - indexFromTop + 1)) * 14; // mimic referenced sizing
        width = Math.max(50, Math.min(150, width));

        Rectangle r = new Rectangle(width, DISC_HEIGHT);
        r.setFill(discColors[paletteIndex]);
        r.setStroke(Color.BLACK);
        // Mark as a disc node to filter during reset
        r.setUserData("disc");
        board.getChildren().add(r);
        return r;
    }

    private void placeDiscOnPeg(Rectangle disc, char peg) {
        Deque<Rectangle> stack = pegToDiscs.get(peg);
        double pegX = getPegCenterX(peg);
        int heightIndex = stack.size();
        double y = BASE_TOP_Y - DISC_HEIGHT * (heightIndex + 1);
        double x = pegX - disc.getWidth() / 2.0;

        disc.setTranslateX(0);
        disc.setTranslateY(0);
        disc.setLayoutX(x);
        disc.setLayoutY(y);

        discBaseX.put(disc, x);
        discBaseY.put(disc, y);

        stack.push(disc);
    }

    private double getPegCenterX(char peg) {
        return switch (peg) {
            case 'A' -> pegAX;
            case 'B' -> pegBX;
            case 'C' -> pegCX;
            default -> throw new IllegalArgumentException("Unknown peg: " + peg);
        };
    }

    private void startAnimation() {
        if (isRunning) return;
        isRunning = true;
        startButton.setDisable(true);
        discSpinner.setDisable(true);

        int n = discSpinner.getValue();
        HanoiTowerSolver solver = new HanoiTowerSolver();
        List<HanoiTowerSolver.Move> moves = solver.solve(n, 'A', 'B', 'C'); // Move to B as in reference HTML

        sequence = new SequentialTransition();
        sequence.setOnFinished(e -> {
            isRunning = false;
            startButton.setDisable(false);
            discSpinner.setDisable(false);
        });

        // Build animation steps using a virtual stack model to avoid mutating
        // real stacks before animations execute.
        Map<Character, Deque<Rectangle>> virtualStacks = new HashMap<>();
        virtualStacks.put('A', new ArrayDeque<>(pegToDiscs.get('A')));
        virtualStacks.put('B', new ArrayDeque<>(pegToDiscs.get('B')));
        virtualStacks.put('C', new ArrayDeque<>(pegToDiscs.get('C')));

        for (HanoiTowerSolver.Move move : moves) {
            addAnimatedMove(sequence, move, virtualStacks);
        }

        double speed = speedSlider.getValue();
        sequence.setRate(speed);
        sequence.play();
    }

    private void addAnimatedMove(SequentialTransition seq, HanoiTowerSolver.Move move,
                                 Map<Character, Deque<Rectangle>> virtualStacks) {
        Deque<Rectangle> fromStack = virtualStacks.get(move.from);
        Deque<Rectangle> toStack = virtualStacks.get(move.to);
        Rectangle disc = fromStack.peek();
        if (disc == null) {
            return;
        }

        // Compute target based on current UI state (live), not cached
        double targetPegX = getPegCenterX(move.to);
        double targetX = targetPegX - disc.getWidth() / 2.0;
        double targetY = BASE_TOP_Y - DISC_HEIGHT * (toStack.size() + 1);

        // Lift up
        TranslateTransition lift = new TranslateTransition(Duration.millis(600), disc);
        lift.setToY(PEG_TOP_Y - disc.getLayoutY());

        // Move horizontally
        TranslateTransition across = new TranslateTransition(Duration.millis(600), disc);
        across.setToX(targetX - disc.getLayoutX());

        // Drop down
        TranslateTransition drop = new TranslateTransition(Duration.millis(600), disc);
        drop.setToY(targetY - disc.getLayoutY());

        // After the three transitions, commit the layout and reset translate
        drop.setOnFinished(e -> {
            disc.setLayoutX(targetX);
            disc.setLayoutY(targetY);
            disc.setTranslateX(0);
            disc.setTranslateY(0);
            discBaseX.put(disc, targetX);
            discBaseY.put(disc, targetY);
            // Real stacks mirror the visual state only after the drop completes
            Deque<Rectangle> realFrom = pegToDiscs.get(move.from);
            Deque<Rectangle> realTo = pegToDiscs.get(move.to);
            // Ensure disc is removed from realFrom if still present
            if (!realFrom.isEmpty() && realFrom.peek() == disc) {
                realFrom.pop();
            } else {
                realFrom.remove(disc);
            }
            // Enforce rule: larger cannot be placed on smaller
            Rectangle top = realTo.peek();
            if (top != null && disc.getWidth() > top.getWidth()) {
                // If violation somehow occurs, revert to from peg visually and logically
                double fromPegX = getPegCenterX(move.from);
                double revertX = fromPegX - disc.getWidth() / 2.0;
                double revertY = BASE_TOP_Y - DISC_HEIGHT * (realFrom.size() + 1);
                disc.setLayoutX(revertX);
                disc.setLayoutY(revertY);
                discBaseX.put(disc, revertX);
                discBaseY.put(disc, revertY);
                realFrom.push(disc);
            } else {
                realTo.push(disc);
            }
        });

        seq.getChildren().addAll(lift, across, drop);

        // Update virtual stacks immediately to plan subsequent steps
        fromStack.pop();
        toStack.push(disc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
