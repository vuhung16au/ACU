package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * "Flag & Anthem" Tab showcasing national flags with corresponding anthems.
 *
 * Logic adapted from the FlagAnthem sample at
 * https://liveexample.pearsoncmg.com/html/FlagAnthem.html
 */
public class FlagAnthemTab extends Tab {

    private static final int NUMBER_OF_NATIONS = 7;
    private static final String URL_BASE = "https://liveexample.pearsoncmg.com/common";

    private int currentIndex = 0;

    private final Image[] images = new Image[NUMBER_OF_NATIONS];
    private final MediaPlayer[] players = new MediaPlayer[NUMBER_OF_NATIONS];

    public FlagAnthemTab() {
        super("Flag & Anthem");
        setClosable(false);

        // Load images and audio
        for (int i = 0; i < NUMBER_OF_NATIONS; i++) {
            try {
                images[i] = new Image(URL_BASE + "/image/flag" + i + ".gif", true);
            } catch (Exception ignored) {}
            try {
                players[i] = new MediaPlayer(new Media(
                    URL_BASE + "/audio/anthem/anthem" + i + ".mp3"));
            } catch (Exception ignored) {}
        }

        // UI Controls
        Button btPlayPause = new Button("||");
        ImageView imageView = new ImageView(images[currentIndex]);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(320);

        ObservableList<String> items = FXCollections.observableArrayList(
            "Denmark", "Germany", "China", "India", "Norway", "UK", "US");
        ComboBox<String> cboNation = new ComboBox<>();
        cboNation.getItems().addAll(items);
        cboNation.setValue(items.get(0));

        // Button behavior
        btPlayPause.setOnAction(e -> {
            MediaPlayer mp = players[currentIndex];
            if (mp == null) return;
            if ("%s".formatted(btPlayPause.getText()).equals(">")) {
                btPlayPause.setText("||");
                mp.play();
            } else {
                btPlayPause.setText(">");
                mp.pause();
            }
        });

        // Combo behavior
        cboNation.setOnAction(e -> {
            MediaPlayer mp = players[currentIndex];
            if (mp != null) mp.stop();
            currentIndex = items.indexOf(cboNation.getValue());
            imageView.setImage(images[currentIndex]);
            MediaPlayer newMp = players[currentIndex];
            if (newMp != null) newMp.play();
            btPlayPause.setText("||");
        });

        // Controls row
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(btPlayPause, new Label("Select a nation: "), cboNation);
        hBox.setAlignment(Pos.CENTER);

        // Layout
        BorderPane pane = new BorderPane();
        pane.setCenter(imageView);
        pane.setBottom(hBox);
        setContent(pane);

        // Autoplay first anthem and keep button state in sync on end
        if (players[currentIndex] != null) {
            players[currentIndex].setOnEndOfMedia(() -> btPlayPause.setText(">"));
            players[currentIndex].play();
        } else {
            btPlayPause.setText(">");
        }

        // Pause playback when tab is not selected
        selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            MediaPlayer mp = players[currentIndex];
            if (mp == null) return;
            if (!isSelected) {
                mp.pause();
                btPlayPause.setText(">");
            }
        });
    }
}
