package com.acu.javafx.directory;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.text.NumberFormat;

/**
 * Simple JavaFX UI to compute directory size using a queue-based traversal.
 */
public class DirectorySizeApp extends Application {

    @Override
    public void start(Stage stage) {
        TextField pathField = new TextField();
        pathField.setPromptText("Choose a directory or enter path");
        pathField.setPrefColumnCount(28);

        Button browse = new Button("Browse");
        Button compute = new Button("Compute Size");
        Text result = new Text();

        HBox controls = new HBox(8, new Label("Directory:"), pathField, browse, compute);
        controls.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(controls);
        root.setCenter(result);
        BorderPane.setMargin(result, new Insets(10));

        DirectoryChooser chooser = new DirectoryChooser();

        browse.setOnAction(e -> {
            File chosen = chooser.showDialog(stage);
            if (chosen != null) {
                pathField.setText(chosen.getAbsolutePath());
            }
        });

        compute.setOnAction(e -> {
            File dir = new File(pathField.getText().trim());
            long bytes = DirectorySize.computeSize(dir);
            result.setText("Total size: " + formatBytes(bytes) + " (" + bytes + " bytes)");
        });

        stage.setTitle("Directory Size (Queue)");
        stage.setScene(new Scene(root, 680, 160));
        stage.show();
    }

    private static String formatBytes(long bytes) {
        // Simple human-readable formatting in KB/MB/GB
        double b = bytes;
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int i = 0;
        while (b >= 1024 && i < units.length - 1) {
            b /= 1024;
            i++;
        }
        return NumberFormat.getNumberInstance().format(Math.round(b * 10.0) / 10.0) + " " + units[i];
    }

    public static void main(String[] args) {
        launch(args);
    }
}


