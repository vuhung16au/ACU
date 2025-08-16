package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Tab demonstrating selection controls: ComboBox, ListView, ScrollBar, Slider.
 */
public class SelectionControlsTab extends Tab {

    public SelectionControlsTab(Text sharedDisplayText) {
        super("Selection Controls");
        setClosable(false);

        BorderPane mainPane = new BorderPane();

        HBox topSection = new HBox(20);
        topSection.setPadding(new Insets(20));

        VBox comboSection = new VBox(10);
        comboSection.getChildren().add(new Label("Select Font:"));

        ComboBox<String> fontComboBox = new ComboBox<>();
        fontComboBox.getItems().addAll("Arial", "Times New Roman", "Courier New", "Helvetica", "Georgia");
        fontComboBox.setValue("Arial");
        fontComboBox.setOnAction(_ -> {
            String selectedFont = fontComboBox.getValue();
            sharedDisplayText.setFont(Font.font(selectedFont, sharedDisplayText.getFont().getSize()));
        });
        comboSection.getChildren().add(fontComboBox);

        VBox listSection = new VBox(10);
        listSection.getChildren().add(new Label("Select Colors (Multiple):"));

        ListView<String> colorListView = new ListView<>();
        ObservableList<String> colors = FXCollections.observableArrayList(
                "Black", "Red", "Green", "Blue", "Orange", "Purple", "Brown"
        );
        colorListView.setItems(colors);
        colorListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        colorListView.setPrefHeight(120);

        Label selectedColorsLabel = new Label("Selected: None");
        colorListView.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> {
            ObservableList<String> selected = colorListView.getSelectionModel().getSelectedItems();
            selectedColorsLabel.setText("Selected: " + String.join(", ", selected));
            if (!selected.isEmpty()) {
                Color color = Color.valueOf(selected.get(0).toLowerCase());
                sharedDisplayText.setFill(color);
            }
        });
        listSection.getChildren().addAll(colorListView, selectedColorsLabel);

        topSection.getChildren().addAll(comboSection, listSection);

        VBox centerSection = new VBox(10);
        centerSection.setPadding(new Insets(20));

        Pane textPane = new Pane();
        textPane.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        textPane.setPrefSize(400, 200);

        Text movableText = new Text("Move me with scrollbars and sliders!");
        movableText.setX(50);
        movableText.setY(50);
        textPane.getChildren().add(movableText);

        HBox scrollBarSection = new HBox(10);
        scrollBarSection.getChildren().add(new Label("ScrollBars:"));

        ScrollBar horizontalScrollBar = new ScrollBar();
        horizontalScrollBar.setOrientation(Orientation.HORIZONTAL);
        horizontalScrollBar.setMin(0);
        horizontalScrollBar.setMax(300);
        horizontalScrollBar.setValue(50);
        horizontalScrollBar.valueProperty().addListener((_, _, newVal) -> movableText.setX(newVal.doubleValue()));

        ScrollBar verticalScrollBar = new ScrollBar();
        verticalScrollBar.setOrientation(Orientation.VERTICAL);
        verticalScrollBar.setMin(20);
        verticalScrollBar.setMax(180);
        verticalScrollBar.setValue(50);
        verticalScrollBar.valueProperty().addListener((_, _, newVal) -> movableText.setY(newVal.doubleValue()));

        HBox sliderSection = new HBox(10);
        sliderSection.getChildren().add(new Label("Sliders (Font Size & Rotation):"));

        Slider fontSizeSlider = new Slider(8, 48, 16);
        fontSizeSlider.setShowTickLabels(true);
        fontSizeSlider.setShowTickMarks(true);
        fontSizeSlider.setMajorTickUnit(8);
        fontSizeSlider.valueProperty().addListener((_, _, newVal) ->
                movableText.setFont(Font.font(movableText.getFont().getFamily(), newVal.doubleValue())));

        Slider rotationSlider = new Slider(-180, 180, 0);
        rotationSlider.setShowTickLabels(true);
        rotationSlider.setShowTickMarks(true);
        rotationSlider.setMajorTickUnit(45);
        rotationSlider.valueProperty().addListener((_, _, newVal) -> movableText.setRotate(newVal.doubleValue()));

        VBox sliderControls = new VBox(10);
        sliderControls.getChildren().addAll(new Label("Font Size:"), fontSizeSlider,
                new Label("Rotation:"), rotationSlider);

        BorderPane scrollBarPane = new BorderPane();
        scrollBarPane.setCenter(textPane);
        scrollBarPane.setBottom(horizontalScrollBar);
        scrollBarPane.setRight(verticalScrollBar);

        centerSection.getChildren().addAll(scrollBarPane, sliderControls);

        mainPane.setTop(topSection);
        mainPane.setCenter(centerSection);

        setContent(mainPane);
    }
}
