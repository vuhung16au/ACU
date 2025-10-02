package com.acu.javafx.externalsort;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Minimal JavaFX UI to demonstrate creating a large file and external sorting it.
 * The heavy lifting is done by {@link CreateLargeFile} and {@link SortLargeFile}.
 */
public class ExternalSortApp extends Application {

    @Override
    public void start(Stage stage) {
        TextField numLinesField = new TextField("100000");
        TextField chunkSizeField = new TextField("50000");
        TextField inputField = new TextField("data/large-input.txt");
        TextField outputField = new TextField("data/sorted-output.txt");
        TextArea logArea = new TextArea();
        logArea.setEditable(false);

        Button createBtn = new Button("Create Large File");
        createBtn.setOnAction(e -> {
            try {
                Path input = Path.of(inputField.getText());
                int lines = Integer.parseInt(numLinesField.getText());
                CreateLargeFile.generateRandomIntegersFile(input, lines);
                logArea.appendText("Created: " + input + " with " + lines + " lines\n");
            } catch (IOException ex) {
                logArea.appendText("Error: " + ex.getMessage() + "\n");
            }
        });

        Button sortBtn = new Button("External Sort");
        sortBtn.setOnAction(e -> {
            try {
                Path input = Path.of(inputField.getText());
                Path output = Path.of(outputField.getText());
                int chunkSize = Integer.parseInt(chunkSizeField.getText());
                SortLargeFile.externalSort(input, output, chunkSize);
                logArea.appendText("Sorted -> " + output + "\n");
            } catch (IOException ex) {
                logArea.appendText("Error: " + ex.getMessage() + "\n");
            }
        });

        GridPane root = new GridPane();
        root.setPadding(new Insets(12));
        root.setHgap(8);
        root.setVgap(8);

        int r = 0;
        root.add(new Label("Num lines:"), 0, r);
        root.add(numLinesField, 1, r++);
        root.add(new Label("Chunk size:"), 0, r);
        root.add(chunkSizeField, 1, r++);
        root.add(new Label("Input file:"), 0, r);
        root.add(inputField, 1, r++);
        root.add(new Label("Output file:"), 0, r);
        root.add(outputField, 1, r++);
        root.add(createBtn, 0, r);
        root.add(sortBtn, 1, r++);
        root.add(logArea, 0, r, 2, 1);

        stage.setTitle("External Sort Demo");
        stage.setScene(new Scene(root, 720, 420));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


