package com.acu.javafx.storelist;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * JavaFX UI: enter unique integers and store in LinkedList; sort/shuffle/reverse.
 * Keeps code minimal and commented for students.
 */
public class App extends Application {
    private final NumberList model = new NumberList();

    private final TextField input = new TextField();
    private final TextArea output = new TextArea();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Store Numbers in a Linked List");

        // Top: label + text field
        Label label = new Label("Enter a number:");
        input.setPrefColumnCount(10);
        input.setOnAction(e -> addNumber());
        HBox top = new HBox(8, label, input);
        top.setAlignment(Pos.CENTER_LEFT);
        top.setPadding(new Insets(10));

        // Center: text area shows the list
        output.setEditable(false);
        output.setWrapText(false);

        // Bottom: buttons
        Button btnSort = new Button("Sort");
        Button btnShuffle = new Button("Shuffle");
        Button btnReverse = new Button("Reverse");
        btnSort.setOnAction(e -> { model.sort(); refresh(); });
        btnShuffle.setOnAction(e -> { model.shuffle(); refresh(); });
        btnReverse.setOnAction(e -> { model.reverse(); refresh(); });
        HBox bottom = new HBox(12, btnSort, btnShuffle, btnReverse);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(output);
        root.setBottom(bottom);

        Scene scene = new Scene(root, 520, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void addNumber() {
        String text = input.getText().trim();
        if (text.isEmpty()) return;
        try {
            int value = Integer.parseInt(text);
            // Only unique insertions are kept by the model
            model.addUnique(value);
            refresh();
        } catch (NumberFormatException ignore) {
            // ignore invalid input for simplicity in this educational app
        } finally {
            input.clear();
        }
    }

    private void refresh() {
        output.setText(model.joinWithSpaces());
    }

    public static void main(String[] args) {
        launch(args);
    }
}


