package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Tab that demonstrates a bouncing ball with a slider controlling speed.
 * Uses {@link BallPane} for animation and exposes play/pause controls.
 */
public class SliderDemo extends Tab {

  public SliderDemo() {
    super("Bouncing Ball - Slider Demo");
    setClosable(false);

    BallPane ballPane = new BallPane();

    // Speed slider controlling animation rate
    Slider speedSlider = new Slider(0, 5, 1); // 0 = paused, 1 = normal, up to 5x
    speedSlider.setShowTickLabels(true);
    speedSlider.setShowTickMarks(true);
    speedSlider.setMajorTickUnit(1);
    speedSlider.setBlockIncrement(0.1);

    Label speedLabel = new Label("Speed: 1.0x");
    speedSlider.valueProperty().addListener((obs, oldV, newV) -> {
      // Touch obs and oldV to avoid unused-parameter warnings in strict linters
      if (oldV != null && obs != null) { /* no-op */ }
      double rate = Math.max(0.0, newV.doubleValue());
      ballPane.rateProperty().set(rate);
      speedLabel.setText(String.format("Speed: %.1fx", rate));
      if (rate == 0.0) {
        ballPane.pause();
      } else {
        ballPane.play();
      }
    });

    // Buttons for quick control
    Button playBtn = new Button("Play");
    Button pauseBtn = new Button("Pause");
    Button slowerBtn = new Button("- Speed");
    Button fasterBtn = new Button("+ Speed");

    playBtn.setOnAction(_ -> {
      if (speedSlider.getValue() == 0) speedSlider.setValue(1);
      ballPane.play();
    });
    pauseBtn.setOnAction(_ -> {
      speedSlider.setValue(0);
      ballPane.pause();
    });
    slowerBtn.setOnAction(_ -> speedSlider.setValue(Math.max(0, speedSlider.getValue() - 0.1)));
    fasterBtn.setOnAction(_ -> speedSlider.setValue(Math.min(speedSlider.getMax(), speedSlider.getValue() + 0.1)));

    HBox controls = new HBox(10, new Label("Speed:"), speedSlider, speedLabel, playBtn, pauseBtn, slowerBtn, fasterBtn);
    controls.setAlignment(Pos.CENTER);
    controls.setPadding(new Insets(10));

    BorderPane root = new BorderPane();
    root.setCenter(ballPane);
    root.setBottom(controls);
    root.setPadding(new Insets(10));

    setContent(root);

    // Initialize with slider's default rate
    ballPane.rateProperty().set(speedSlider.getValue());
  }
}