package com.acu.javafx.calculator.controllers;

import com.acu.javafx.calculator.model.Calculations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller for the scientific calculator view.
 *
 * Extends the normal keypad with scientific functions such as absolute value,
 * exponent, factorial, natural log, power, square root, π constant, and a
 * quick integer-to-binary converter. UI events still only update text fields
 * and labels; calculation is delegated to {@link Calculations}.
 */
public class ScientificController extends NormalController {
    Calculations calc = new Calculations();
    @FXML
    private Button buttonAbsolute;
    @FXML
    private Button buttonExponent;
    @FXML
    private Button buttonFactorial;
    @FXML
    private Button buttonBinary;
    @FXML
    private Button buttonNaturalLog;
    @FXML
    private Button buttonModulo;
    @FXML
    private Button buttonPi;
    @FXML
    private Button buttonPower;
    @FXML
    private Button buttonSquareRoot;

    /**
     * Initializes scientific controller after FXML is loaded.
     * Sets initial answer to 0.00 and wires both normal and scientific buttons.
     */
    @Override
    public void initialize() {
        double answer = 0;
        labelAnswer.setText(String.format("%.02f", answer));
        initializeButtonActions();
    }

    /**
     * Navigates from scientific back to normal view.
     */
    public void handleSwitchToNormal(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("normal-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Calculator");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * All the buttons setOnAction functions.
     */
    /**
     * Wires scientific buttons and reuses base keypad bindings.
     */
    public void initializeButtonActions() {
        super.initializeButtonActions();
        buttonExponent.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "e^(" : "e^("));

        buttonAbsolute.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "||" : "||"));

        buttonFactorial.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "!" : "!"));

        buttonBinary.setOnAction(event -> {
            if (calc.controlToBin(inputField.getText())) {
                labelAnswer.setText(Integer.toBinaryString(Integer.parseInt(inputField.getText())));
            } else {
                labelAnswer.setText(null);
                calc.handleError("Convert only numbers");
            }
        });

        buttonNaturalLog.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "log(" : "log("));

        buttonPower.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "^" : "^"));

        buttonModulo.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "%" : "%"));

        buttonSquareRoot.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "sqrt(" : "sqrt("));

        buttonPi.setOnAction(event -> inputField.setText(inputField.getText() != null ? inputField.getText() + "π" : "π"));
    }
}


