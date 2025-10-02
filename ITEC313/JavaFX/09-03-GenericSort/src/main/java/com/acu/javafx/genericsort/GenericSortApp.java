package com.acu.javafx.genericsort;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Comparator;

/** Simple UI to demonstrate generic sorts on integers. */
public class GenericSortApp extends Application {

    @Override
    public void start(Stage stage) {
        TextArea input = new TextArea("5,3,8,1,2,9,4,7,6");
        input.setPrefRowCount(3);
        TextArea output = new TextArea();
        output.setPrefRowCount(3);

        ComboBox<String> algo = new ComboBox<>();
        algo.getItems().addAll("Bubble", "Merge", "Quick");
        algo.getSelectionModel().selectFirst();

        ComboBox<String> order = new ComboBox<>();
        order.getItems().addAll("Ascending", "Descending");
        order.getSelectionModel().selectFirst();

        Button sortBtn = new Button("Sort");
        sortBtn.setOnAction(e -> {
            Integer[] data = parse(input.getText());
            Comparator<Integer> cmp = order.getValue().equals("Ascending")
                    ? Comparator.naturalOrder() : Comparator.reverseOrder();
            switch (algo.getValue()) {
                case "Bubble" -> GenericSorts.bubbleSort(data, cmp);
                case "Merge" -> GenericSorts.mergeSort(data, cmp);
                case "Quick" -> GenericSorts.quickSort(data, cmp);
            }
            output.setText(Arrays.toString(data));
        });

        HBox controls = new HBox(10, new Label("Algorithm:"), algo,
                new Label("Order:"), order, sortBtn);
        controls.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(controls);
        root.setCenter(new VBoxLike("Input", input, "Output", output));
        BorderPane.setMargin(root.getCenter(), new Insets(10));

        stage.setTitle("Generic Sort Demo");
        stage.setScene(new Scene(root, 640, 360));
        stage.show();
    }

    private Integer[] parse(String s) {
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .filter(t -> !t.isEmpty())
                .map(Integer::valueOf)
                .toArray(Integer[]::new);
    }

    // Minimal helper to stack labeled areas without bringing additional FXML
    private static class VBoxLike extends BorderPane {
        VBoxLike(String t1, TextArea a1, String t2, TextArea a2) {
            BorderPane top = new BorderPane();
            top.setTop(new Label(t1));
            top.setCenter(a1);
            top.setPadding(new Insets(5));
            BorderPane bottom = new BorderPane();
            bottom.setTop(new Label(t2));
            bottom.setCenter(a2);
            bottom.setPadding(new Insets(5));
            setTop(top);
            setCenter(bottom);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


