package com.example;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Tab demonstrating input controls like TextField, PasswordField, TextArea.
 */
public class InputControlsTab extends Tab {

    public InputControlsTab(Text sharedDisplayText, Font normalFont) {
        super("Input Controls");
        setClosable(false);

        VBox mainPane = new VBox(20);
        mainPane.setPadding(new Insets(20));

        VBox textFieldSection = new VBox(10);
        textFieldSection.getChildren().add(new Label("Text Input:"));

        TextField textField = new TextField();
        textField.setPromptText("Enter text here...");
        textField.setOnAction(_ -> sharedDisplayText.setText(textField.getText()));
        textFieldSection.getChildren().add(textField);

        VBox passwordSection = new VBox(10);
        passwordSection.getChildren().add(new Label("Password Input:"));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password...");

        Label passwordLabel = new Label("Password will appear here when entered");
        passwordField.setOnAction(_ -> {
            if (!passwordField.getText().isEmpty()) {
                passwordLabel.setText("Password entered: " + "*".repeat(passwordField.getText().length()));
            }
        });
        passwordSection.getChildren().addAll(passwordField, passwordLabel);

        VBox textAreaSection = new VBox(10);
        textAreaSection.getChildren().add(new Label("Multi-line Text Input:"));

        TextArea textArea = new TextArea();
        textArea.setPromptText("Enter multiple lines of text...");
        textArea.setPrefRowCount(5);
        textArea.setWrapText(true);

        Button updateFromTextArea = new Button("Update Display Text");
        updateFromTextArea.setOnAction(_ -> {
            if (!textArea.getText().isEmpty()) {
                String[] lines = textArea.getText().split("\n");
                sharedDisplayText.setText(lines[0]);
            }
        });
        textAreaSection.getChildren().addAll(textArea, updateFromTextArea);

        Pane textPane = new Pane();
        Text localDisplayText = new Text(sharedDisplayText.getText());
        localDisplayText.setX(50);
        localDisplayText.setY(50);
        localDisplayText.setFont(normalFont);
        textPane.getChildren().add(localDisplayText);
        textPane.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        textPane.setPrefSize(400, 100);

        localDisplayText.textProperty().bind(sharedDisplayText.textProperty());

        mainPane.getChildren().addAll(
                textFieldSection,
                passwordSection,
                textAreaSection,
                new Label("Display Area:"),
                textPane
        );

        ScrollPane scrollPane = new ScrollPane(mainPane);
        scrollPane.setFitToWidth(true);
        setContent(scrollPane);
    }
}
