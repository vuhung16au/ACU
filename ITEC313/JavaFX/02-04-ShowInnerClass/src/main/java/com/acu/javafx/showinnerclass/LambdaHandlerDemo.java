package com.acu.javafx.showinnerclass;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * LambdaHandlerDemo.java: Demonstrate using lambda expressions for event handling.
 *
 * This demo mirrors the AnonymousHandlerDemo but replaces the anonymous inner
 * EventHandler classes with concise lambda expressions. Because the single
 * abstract method in EventHandler<T> makes it a functional interface, it can
 * be implemented with a lambda instead of a full anonymous class.
 *
 * Key concepts demonstrated:
 *  - Replacing anonymous inner classes with lambdas
 *  - Cleaner, more readable event-handling code
 *  - Maintaining identical behavior to the anonymous class implementation
 */
public class LambdaHandlerDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        Text text = new Text(40, 40, "Programming with Lambdas");
        Pane pane = new Pane(text);

        // Buttons
        Button btUp = new Button("Up");
        Button btDown = new Button("Down");
        Button btLeft = new Button("Left");
        Button btRight = new Button("Right");
        HBox hBox = new HBox(btUp, btDown, btLeft, btRight);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane(pane);
        borderPane.setBottom(hBox);

        // Lambda expressions for event handling (functional interface EventHandler<ActionEvent>)
    btUp.setOnAction(event -> text.setY(text.getY() > 10 ? text.getY() - 5 : 10));

    btDown.setOnAction(event -> text.setY(
            text.getY() < pane.getHeight() - text.getLayoutBounds().getHeight() - 10 ?
                text.getY() + 5 : pane.getHeight() - text.getLayoutBounds().getHeight() - 10));

    btLeft.setOnAction(event -> text.setX(text.getX() > 0 ? text.getX() - 5 : 0));

    btRight.setOnAction(event -> text.setX(
            text.getX() < pane.getWidth() - text.getLayoutBounds().getWidth() - 10 ?
                text.getX() + 5 : pane.getWidth() - text.getLayoutBounds().getWidth() - 10));

        Scene scene = new Scene(borderPane, 400, 350);
        primaryStage.setTitle("LambdaHandlerDemo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
