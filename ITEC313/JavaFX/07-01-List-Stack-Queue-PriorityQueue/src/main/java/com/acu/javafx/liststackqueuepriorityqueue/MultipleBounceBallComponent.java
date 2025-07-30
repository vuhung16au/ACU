package com.acu.javafx.liststackqueuepriorityqueue;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Component version of MultipleBounceBall that can be used within other JavaFX applications
 */
public class MultipleBounceBallComponent {
    
    /**
     * Creates and shows the MultipleBounceBall demo in a new window
     */
    public static void showDemo() {
        MultipleBounceBall.MultipleBallPane ballPane = new MultipleBounceBall.MultipleBallPane();
        ballPane.setStyle("-fx-border-color: yellow");

        Button btAdd = new Button("+");
        Button btSubtract = new Button("-");
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(btAdd, btSubtract);
        hBox.setAlignment(Pos.CENTER);

        // Add or remove a ball
        btAdd.setOnAction(e -> ballPane.add());
        btSubtract.setOnAction(e -> ballPane.subtract());

        // Pause and resume animation
        ballPane.setOnMousePressed(e -> ballPane.pause());
        ballPane.setOnMouseReleased(e -> ballPane.play());

        // Use a scroll bar to control animation speed
        ScrollBar sbSpeed = new ScrollBar();
        sbSpeed.setMax(20);
        sbSpeed.setValue(10);
        ballPane.rateProperty().bind(sbSpeed.valueProperty());
        
        BorderPane pane = new BorderPane();
        pane.setCenter(ballPane);
        pane.setTop(sbSpeed);
        pane.setBottom(hBox);

        // Create a new stage and scene
        Stage stage = new Stage();
        Scene scene = new Scene(pane, 250, 150);
        stage.setTitle("MultipleBounceBall Demo");
        stage.setScene(scene);
        stage.show();
        
        // Add some initial balls after the stage is shown
        stage.setOnShown(e -> {
            ballPane.add(); // Add first ball
            ballPane.add(); // Add second ball
            ballPane.add(); // Add third ball
        });
    }
} 