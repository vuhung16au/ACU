package com.acu.javafx.hangman;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Minimal Hangman GUI: type letters; ENTER starts a new game.
 */
public class App extends Application {
    private HangmanGame game;
    private final Canvas canvas = new Canvas(480, 320);
    private final Label guessLabel = new Label();
    private final Label missedLabel = new Label();

    @Override
    public void start(Stage stage) {
        newGame();

        VBox info = new VBox(4, guessLabel, missedLabel);
        info.setPadding(new Insets(8));

        BorderPane root = new BorderPane();
        root.setTop(info);
        root.setCenter(canvas);

        Scene scene = new Scene(root, 520, 380);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && (game.isWon() || game.isLost())) {
                newGame();
            } else if (e.getText() != null && e.getText().length() == 1) {
                char ch = e.getText().charAt(0);
                if (Character.isLetter(ch) && !game.isWon() && !game.isLost()) {
                    game.guess(ch);
                    refresh();
                }
            }
        });

        stage.setScene(scene);
        stage.setTitle("Hangman Game");
        stage.show();
        refresh();
    }

    private void newGame() {
        game = new HangmanGame();
        refresh();
    }

    private void refresh() {
        guessLabel.setText("Guess a word: " + game.getMaskedWord());
        missedLabel.setText("Missed letters: " + game.getMissedLetters());
        drawGallowsAndMan(game.missedCount());
    }

    private void drawGallowsAndMan(int misses) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setStroke(Color.BLACK);
        g.setLineWidth(2);
        // Gallows
        g.strokeLine(60, 280, 200, 280); // base
        g.strokeLine(100, 280, 100, 60); // pole
        g.strokeLine(100, 60, 220, 60);  // top beam
        g.strokeLine(220, 60, 220, 90);  // rope

        // Draw hangman parts progressively (7 parts)
        if (misses > 0) g.strokeOval(200, 90, 40, 40);       // head
        if (misses > 1) g.strokeLine(220, 130, 220, 200);    // body
        if (misses > 2) g.strokeLine(220, 150, 190, 170);    // left arm
        if (misses > 3) g.strokeLine(220, 150, 250, 170);    // right arm
        if (misses > 4) g.strokeLine(220, 200, 195, 240);    // left leg
        if (misses > 5) g.strokeLine(220, 200, 245, 240);    // right leg
        if (misses > 6) g.strokeLine(220, 60, 240, 80);      // small tilt to mimic swing

        if (game.isWon()) {
            g.setFill(Color.DARKGREEN);
            g.fillText("You won! Press ENTER for new word", 240, 300);
        } else if (game.isLost()) {
            g.setFill(Color.DARKRED);
            g.fillText("The word is: " + game.getWord() + "  (ENTER for new)", 240, 300);
        }
    }

    public static void main(String[] args) { launch(args); }
}


