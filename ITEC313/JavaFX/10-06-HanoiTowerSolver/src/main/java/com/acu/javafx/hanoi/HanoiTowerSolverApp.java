package com.acu.javafx.hanoi;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Simple JavaFX UI to demonstrate the non-recursive Hanoi solver.
 */
public class HanoiTowerSolverApp extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("Disks:");
        Spinner<Integer> disksSpinner = new Spinner<>();
        disksSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 3));

        Button solveBtn = new Button("Solve");
        TextArea output = new TextArea();
        output.setEditable(false);
        output.setWrapText(true);

        HBox controls = new HBox(8, label, disksSpinner, solveBtn);
        controls.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(controls);
        root.setCenter(output);
        BorderPane.setMargin(output, new Insets(10));

        solveBtn.setOnAction(e -> {
            int n = disksSpinner.getValue();
            HanoiTowerSolver solver = new HanoiTowerSolver();
            List<HanoiTowerSolver.Move> moves = solver.solve(n, 'A', 'C', 'B');
            StringBuilder sb = new StringBuilder();
            sb.append("Moves: ").append(moves.size()).append('\n');
            for (int i = 0; i < moves.size(); i++) {
                sb.append(i + 1).append(": ").append(moves.get(i)).append('\n');
            }
            output.setText(sb.toString());
        });

        stage.setTitle("Hanoi Tower Solver (Stack)");
        stage.setScene(new Scene(root, 520, 420));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


