package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Tab containing basic JavaFX controls: Labels, Buttons, CheckBoxes, RadioButtons.
 * This tab owns the display Text node provided by the main application.
 */
public class BasicControlsTab extends Tab {

    private final Text displayText;

    public BasicControlsTab(Text sharedDisplayText, Font normalFont) {
        super("Basic Controls");
        setClosable(false);
        this.displayText = sharedDisplayText;

        BorderPane mainPane = new BorderPane();

        VBox labelSection = createLabelSection();

        Pane textPane = new Pane();
        displayText.setX(50);
        displayText.setY(100);
        displayText.setFont(normalFont);
        displayText.setFill(Color.BLACK);
        textPane.getChildren().add(displayText);
        textPane.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        textPane.setPrefSize(400, 150);

        VBox radioSection = createRadioButtonSection();
        VBox checkBoxSection = createCheckBoxSection();
        HBox buttonSection = createButtonSection();

        mainPane.setTop(labelSection);
        mainPane.setCenter(textPane);
        mainPane.setLeft(radioSection);
        mainPane.setRight(checkBoxSection);
        mainPane.setBottom(buttonSection);

        setContent(mainPane);
    }

    private VBox createLabelSection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(10));
        section.getChildren().add(new Label("Labels with Graphics:"));

        HBox labelPane = new HBox(20);
        labelPane.setAlignment(Pos.CENTER);

        Label circleLabel = new Label("Circle", new Circle(25, Color.LIGHTBLUE));
        circleLabel.setContentDisplay(ContentDisplay.TOP);

        Label rectLabel = new Label("Rectangle", new Rectangle(50, 30, Color.LIGHTGREEN));
        rectLabel.setContentDisplay(ContentDisplay.BOTTOM);

        Ellipse ellipse = new Ellipse(40, 25);
        ellipse.setFill(Color.LIGHTYELLOW);
        ellipse.setStroke(Color.ORANGE);
        Label ellipseLabel = new Label("Ellipse", ellipse);
        ellipseLabel.setContentDisplay(ContentDisplay.LEFT);

        labelPane.getChildren().addAll(circleLabel, rectLabel, ellipseLabel);
        section.getChildren().add(labelPane);
        return section;
    }

    private VBox createRadioButtonSection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(10));
        section.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        section.getChildren().add(new Label("Text Color:"));

        ToggleGroup colorGroup = new ToggleGroup();

        RadioButton blackRadio = new RadioButton("Black");
        RadioButton redRadio = new RadioButton("Red");
        RadioButton blueRadio = new RadioButton("Blue");
        RadioButton greenRadio = new RadioButton("Green");

        blackRadio.setToggleGroup(colorGroup);
        redRadio.setToggleGroup(colorGroup);
        blueRadio.setToggleGroup(colorGroup);
        greenRadio.setToggleGroup(colorGroup);

        blackRadio.setSelected(true);

        blackRadio.setOnAction(_ -> displayText.setFill(Color.BLACK));
        redRadio.setOnAction(_ -> displayText.setFill(Color.RED));
        blueRadio.setOnAction(_ -> displayText.setFill(Color.BLUE));
        greenRadio.setOnAction(_ -> displayText.setFill(Color.GREEN));

        section.getChildren().addAll(blackRadio, redRadio, blueRadio, greenRadio);
        return section;
    }

    private VBox createCheckBoxSection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(10));
        section.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        section.getChildren().add(new Label("Text Style:"));

        CheckBox boldCheckBox = new CheckBox("Bold");
        CheckBox italicCheckBox = new CheckBox("Italic");

        Runnable updateFont = () -> {
            FontWeight weight = boldCheckBox.isSelected() ? FontWeight.BOLD : FontWeight.NORMAL;
            FontPosture posture = italicCheckBox.isSelected() ? FontPosture.ITALIC : FontPosture.REGULAR;
            displayText.setFont(Font.font(displayText.getFont().getFamily(), weight, posture, displayText.getFont().getSize()));
        };

        boldCheckBox.setOnAction(_ -> updateFont.run());
        italicCheckBox.setOnAction(_ -> updateFont.run());

        section.getChildren().addAll(boldCheckBox, italicCheckBox);
        return section;
    }

    private HBox createButtonSection() {
        HBox section = new HBox(20);
        section.setPadding(new Insets(10));
        section.setAlignment(Pos.CENTER);
        section.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");

        Button leftButton = new Button("Left", new Rectangle(15, 10, Color.GRAY));
        Button rightButton = new Button("Right", new Rectangle(15, 10, Color.GRAY));
        Button upButton = new Button("Up", new Rectangle(10, 15, Color.GRAY));
        Button downButton = new Button("Down", new Rectangle(10, 15, Color.GRAY));

        leftButton.setOnAction(_ -> displayText.setX(Math.max(0, displayText.getX() - 10)));
        rightButton.setOnAction(_ -> displayText.setX(displayText.getX() + 10));
        upButton.setOnAction(_ -> displayText.setY(Math.max(20, displayText.getY() - 10)));
        downButton.setOnAction(_ -> displayText.setY(displayText.getY() + 10));

        section.getChildren().addAll(leftButton, rightButton, upButton, downButton);
        return section;
    }
}
