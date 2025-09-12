package com.acu.javafx.balls;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Bouncing balls that combine on collision. Minimal code focused on clarity.
 */
public class App extends Application {
    private final BallPane ballPane = new BallPane();
    private final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> ballPane.step()));

    @Override
    public void start(Stage stage) {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Button suspend = new Button("Suspend");
        suspend.setOnAction(e -> timeline.pause());
        Button resume = new Button("Resume");
        resume.setOnAction(e -> timeline.play());
        Button add = new Button("+");
        add.setOnAction(e -> ballPane.addRandomBall());
        Button remove = new Button("-");
        remove.setOnAction(e -> ballPane.removeOneBall());

        HBox controls = new HBox(10, suspend, resume, add, remove);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(8));

        BorderPane root = new BorderPane();
        root.setCenter(ballPane);
        root.setBottom(controls);

        Scene scene = new Scene(root, 640, 420);
        stage.setScene(scene);
        stage.setTitle("Combine Colliding Bouncing Balls");
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}


