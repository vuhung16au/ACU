package com.acu.car.management.controller;

import com.acu.car.management.persistence.model.Component;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.UUID;

/**
 * Dialog for adding new components to cars
 */
public class AddComponentDialog extends Dialog<Component> {
    // UI components for component input fields
    @FXML private TextField tfComponentCode;
    @FXML private TextField tfDescription;
    @FXML private TextField tfCost;

    /**
     * Initialize the dialog with a random component code
     */
    public void initialize() {
        tfComponentCode.setText(UUID.randomUUID().toString().substring(0, 8));
    }

    /**
     * Constructor that sets up the dialog UI and result handling
     */
    public AddComponentDialog() throws IOException {
        super();
        // Load the FXML layout
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("add-component-view.fxml"));
        loader.setController(this);
        setDialogPane(loader.load());
        
        // Configure dialog properties
        setTitle("Add Component");
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        
        // Handle dialog result
        setResultConverter(buttonType -> {
            if (buttonType == ButtonType.APPLY) {
                return new Component(null,
                        tfComponentCode.getText(),
                        tfDescription.getText(),
                        Double.parseDouble(tfCost.getText()));
            }
            return null;
        });
    }
}
