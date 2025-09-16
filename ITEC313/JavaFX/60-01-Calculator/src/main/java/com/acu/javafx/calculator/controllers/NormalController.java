package com.acu.javafx.calculator.controllers;

import com.acu.javafx.calculator.model.Calculations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller for the normal calculator view.
 *
 * Handles numeric and basic operator inputs, the display field, and transitions
 * to the scientific view. All event handlers only manipulate UI text; evaluation
 * is delegated to {@link Calculations}.
 */
public class NormalController {
    @FXML
    public Button buttonZero;

    @FXML
    public Button buttonOne;

    @FXML
    public Button buttonTwo;

    @FXML
    public Button buttonThree;

    @FXML
    public Button buttonFour;

    @FXML
    public Button buttonFive;

    @FXML
    public Button buttonSix;

    @FXML
    public Button buttonSeven;

    @FXML
    public Button buttonEight;

    @FXML
    public Button buttonNine;

    @FXML
    public Button buttonBackspace;

    @FXML
    public Button buttonClearAll;

    @FXML
    public Button buttonDivide;

    @FXML
    public Button buttonEquals;

    @FXML
    public Button buttonMultiply;

    @FXML
    public Button buttonDecimal;

    @FXML
    public Button buttonSubtract;

    @FXML
    public Button buttonAdd;

    @FXML
    public Label labelAnswer;

    @FXML
    public TextField inputField;
    public Stage stage;
    public Scene scene;
    public Parent root;
    Calculations calc = new Calculations();

    /**
     * Initializes the controller after the FXML is loaded.
     * Sets the initial answer to 0.00 and wires button actions.
     */
    public void initialize() {
        double answer = 0;
        labelAnswer.setText(String.format("%.02f", answer));
        initializeButtonActions();
    }

    /**
     * Navigates from normal to scientific view.
     * Keeps stage properties consistent (title and resizability).
     */
    public void handleSwitchToScientific(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scientific-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Scientific Calculator");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * All the buttons setOnAction functions.
     */
    /**
     * Wires all button actions for the normal calculator keypad.
     * Does not perform any computation directly; it updates the input field
     * or delegates to {@link Calculations#evaluate(String)} for '='.
     */
    protected void initializeButtonActions() {
        buttonBackspace.setOnAction(event -> {
            int length = inputField.getText().length();
            if (length != 0) {
                inputField.setText(inputField.getText().substring(0, length - 1));
            }
        });

        buttonClearAll.setOnAction(event -> inputField.clear());

        buttonDecimal.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "." : "0."));

        buttonEquals.setOnAction(event -> {
            try {
                labelAnswer.setText(calc.evaluate(inputField.getText()));
            } catch (UnknownFunctionOrVariableException u) {
                labelAnswer.setText(null);
                calc.handleError(u.toString().substring(69));
            } catch (IllegalArgumentException i) {
                labelAnswer.setText(null);
                calc.handleError(i.toString().substring(35));
            } catch (ArithmeticException e) {
                labelAnswer.setText(null);
                calc.handleError(e.toString().substring(31));
            }
        });

        buttonAdd.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "+" : "+"));

        buttonSubtract.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "-" : "-"));

        buttonMultiply.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "*" : "*"));

        buttonDivide.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "/" : "/"));

        buttonZero.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "0" : "0"));

        buttonOne.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "1" : "1"));

        buttonTwo.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "2" : "2"));

        buttonThree.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "3" : "3"));

        buttonFour.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "4" : "4"));

        buttonFive.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "5" : "5"));

        buttonSix.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "6" : "6"));

        buttonSeven.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "7" : "7"));

        buttonEight.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "8" : "8"));

        buttonNine.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "9" : "9"));
    }
}


