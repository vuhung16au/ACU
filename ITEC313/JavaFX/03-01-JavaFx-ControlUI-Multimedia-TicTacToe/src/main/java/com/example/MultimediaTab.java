package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
// ScrollPane is in javafx.scene.control, already covered by the wildcard import above
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * Tab demonstrating multimedia playback (audio) with controls.
 */
public class MultimediaTab extends Tab {

    private MediaPlayer mediaPlayer;

    public MultimediaTab() {
        super("Multimedia");
        setClosable(false);

        VBox mainPane = new VBox(20);
        mainPane.setPadding(new Insets(20));
        mainPane.setAlignment(Pos.CENTER);

    // Audio section
    try {
            java.io.File mediaFile = new java.io.File("media/sound-design-elements-sfx-ps-022-302865.mp3");
            String mediaUrl;

            if (mediaFile.exists()) {
                mediaUrl = mediaFile.toURI().toString();
            } else {
                mediaFile = new java.io.File("../media/sound-design-elements-sfx-ps-022-302865.mp3");
                if (mediaFile.exists()) {
                    mediaUrl = mediaFile.toURI().toString();
                } else {
                    throw new Exception("MP3 file not found in any expected location");
                }
            }

            System.out.println("Loading media from: " + mediaUrl);
            Media media = new Media(mediaUrl);
            mediaPlayer = new MediaPlayer(media);

            VBox audioPlayerPane = new VBox(10);
            audioPlayerPane.setAlignment(Pos.CENTER);
            audioPlayerPane.setStyle("-fx-border-color: lightgray; -fx-border-width: 2; -fx-padding: 20;");

            Label audioLabel = new Label("🎵 Audio Player");
            audioLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

            Label fileLabel = new Label("Playing: sound-design-elements-sfx-ps-022-302865.mp3");
            fileLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            fileLabel.setStyle("-fx-text-fill: gray;");

            audioPlayerPane.getChildren().addAll(audioLabel, fileLabel);

            HBox mediaControls = createMediaControls();

            mainPane.getChildren().addAll(new Label("Media Player Demo"), audioPlayerPane, mediaControls);
        } catch (Exception e) {
            Label errorLabel = new Label("Media playback not available");
            errorLabel.setStyle("-fx-text-fill: red;");

            VBox animationDemo = createAnimationDemo();
            mainPane.getChildren().addAll(errorLabel, new Separator(), animationDemo);
        }

        // Video section (separate from audio try/catch so one doesn't block the other)
        try {
            mainPane.getChildren().add(new Separator());
            Label videoHeader = new Label("Video Player Demo");
            videoHeader.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            mainPane.getChildren().add(videoHeader);

            mainPane.getChildren().add(createVideoSection());
        } catch (Exception ex) {
            Label err = new Label("Video playback not available: " + ex.getMessage());
            err.setStyle("-fx-text-fill: red;");
            mainPane.getChildren().add(err);
        }

        ScrollPane scrollPane = new ScrollPane(mainPane);
        scrollPane.setFitToWidth(true);
        setContent(scrollPane);
    }

    private javafx.scene.layout.BorderPane createVideoSection() throws Exception {
        // Resolve local video path similar to the audio section
        java.io.File videoFile = new java.io.File("media/sample-video.mp4");
        String videoUrl;
        if (videoFile.exists()) {
            videoUrl = videoFile.toURI().toString();
        } else {
            videoFile = new java.io.File("../media/sample-video.mp4");
            if (videoFile.exists()) {
                videoUrl = videoFile.toURI().toString();
            } else {
                throw new Exception("Video file not found in expected locations");
            }
        }

        Media media = new Media(videoUrl);
        MediaPlayer videoPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(videoPlayer);
        mediaView.setPreserveRatio(true);
        mediaView.setFitWidth(650);

        // Controls (based on provided logic)
    Button playButton = new Button(">");
    playButton.setOnAction(_ -> {
            if (playButton.getText().equals(">")) {
                videoPlayer.play();
                playButton.setText("||");
            } else {
                videoPlayer.pause();
                playButton.setText(">");
            }
        });

    Button rewindButton = new Button("<<");
    rewindButton.setOnAction(_ -> videoPlayer.seek(Duration.ZERO));

        Slider slVolume = new Slider();
        slVolume.setPrefWidth(150);
        slVolume.setMinWidth(30);
        slVolume.setValue(50);
        videoPlayer.volumeProperty().bind(slVolume.valueProperty().divide(100));

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(playButton, rewindButton, new Label("Volume"), slVolume);

        // File label and simple frame
        Label fileLabel = new Label("Playing: sample-video.mp4");
        fileLabel.setStyle("-fx-text-fill: gray;");
        VBox topBox = new VBox(5, new Label("🎬 Video Player"), fileLabel);
        topBox.setAlignment(Pos.CENTER);

        javafx.scene.layout.BorderPane pane = new javafx.scene.layout.BorderPane();
        pane.setTop(topBox);
        pane.setCenter(mediaView);
        pane.setBottom(hBox);
        pane.setStyle("-fx-border-color: lightgray; -fx-border-width: 2; -fx-padding: 10;");

        return pane;
    }

    private HBox createMediaControls() {
        VBox controlsContainer = new VBox(10);
        controlsContainer.setAlignment(Pos.CENTER);

        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);

        Button playButton = new Button("▶");
        Button pauseButton = new Button("⏸");
        Button stopButton = new Button("⏹");
        Button rewindButton = new Button("⏪");

        playButton.setStyle("-fx-font-size: 16px; -fx-padding: 5 10 5 10;");
        pauseButton.setStyle("-fx-font-size: 16px; -fx-padding: 5 10 5 10;");
        stopButton.setStyle("-fx-font-size: 16px; -fx-padding: 5 10 5 10;");
        rewindButton.setStyle("-fx-font-size: 16px; -fx-padding: 5 10 5 10;");

        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setPrefWidth(100);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(25);

        Label statusLabel = new Label("Ready to play");
        statusLabel.setStyle("-fx-text-fill: blue;");

        playButton.setOnAction(_ -> {
            if (mediaPlayer != null) {
                mediaPlayer.play();
                statusLabel.setText("Playing...");
                statusLabel.setStyle("-fx-text-fill: green;");
            }
        });
        pauseButton.setOnAction(_ -> {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                statusLabel.setText("Paused");
                statusLabel.setStyle("-fx-text-fill: orange;");
            }
        });
        stopButton.setOnAction(_ -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                statusLabel.setText("Stopped");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        });
        rewindButton.setOnAction(_ -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.ZERO);
                statusLabel.setText("Rewound to start");
                statusLabel.setStyle("-fx-text-fill: blue;");
            }
        });

        volumeSlider.valueProperty().addListener((_, _, newVal) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newVal.doubleValue() / 100.0);
            }
        });

        if (mediaPlayer != null) {
            mediaPlayer.setOnReady(() -> {
                statusLabel.setText("Media loaded successfully");
                statusLabel.setStyle("-fx-text-fill: green;");
            });
            mediaPlayer.setOnError(() -> {
                statusLabel.setText("Error playing media");
                statusLabel.setStyle("-fx-text-fill: red;");
            });
            mediaPlayer.setOnEndOfMedia(() -> {
                statusLabel.setText("Playback finished");
                statusLabel.setStyle("-fx-text-fill: blue;");
            });
        }

        controls.getChildren().addAll(playButton, pauseButton, stopButton, rewindButton, new Label("Volume:"), volumeSlider);
        controlsContainer.getChildren().addAll(controls, statusLabel);

        HBox wrapper = new HBox();
        wrapper.setAlignment(Pos.CENTER);
        wrapper.getChildren().add(controlsContainer);
        return wrapper;
    }

    private VBox createAnimationDemo() {
        VBox demo = new VBox(20);
        demo.setAlignment(Pos.CENTER);

        demo.getChildren().add(new Label("Animation Demo (Fallback)"));

        Circle animatedCircle = new Circle(30, Color.LIGHTBLUE);
        Rectangle animatedRect = new Rectangle(60, 40, Color.LIGHTGREEN);

        HBox shapesPane = new HBox(20);
        shapesPane.setAlignment(Pos.CENTER);
        shapesPane.getChildren().addAll(animatedCircle, animatedRect);

        Button animateButton = new Button("Animate Colors");
        animateButton.setOnAction(_ -> {
            animatedCircle.setFill(animatedCircle.getFill() == Color.LIGHTBLUE ? Color.ORANGE : Color.LIGHTBLUE);
            animatedRect.setFill(animatedRect.getFill() == Color.LIGHTGREEN ? Color.PINK : Color.LIGHTGREEN);
        });

        demo.getChildren().addAll(shapesPane, animateButton);
        return demo;
    }
}
