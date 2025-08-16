package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

/**
 * Tab wrapper for the TicTacToe game UI.
 */
public class GameTab extends Tab {

    public GameTab() {
        super("Tic-Tac-Toe Game");
        setClosable(false);

        VBox mainPane = new VBox(20);
        mainPane.setPadding(new Insets(20));
        mainPane.setAlignment(Pos.CENTER);

        TicTacToeGame game = new TicTacToeGame();
        mainPane.getChildren().addAll(new Label("Tic-Tac-Toe Game"), game.getGamePane());

        setContent(mainPane);
    }
}
