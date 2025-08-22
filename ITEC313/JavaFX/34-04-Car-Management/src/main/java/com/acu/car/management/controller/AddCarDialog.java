package com.acu.car.management.controller;

import com.acu.car.management.CarManagementApp;
import com.acu.car.management.persistence.model.Car;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.io.IOException;

/**
 * Dialog for adding new cars to the system
 */
public class AddCarDialog extends Dialog<Car> {
    // UI components for car input fields
    @FXML private ComboBox<String> cbCategory;
    @FXML private TextField tfMake;
    @FXML private TextField tfModel;
    @FXML private TextField tfYear;
    @FXML private TextField tfColor;
    @FXML private TextField tfMileage;

    /**
     * Initialize the dialog with car categories and default selection
     */
    public void initialize() {
        cbCategory.getItems().removeAll();
        cbCategory.getItems().addAll(CarManagementApp.carTypes);
        cbCategory.getSelectionModel().select("Sedan");
    }

    /**
     * Constructor that sets up the dialog UI and result handling
     */
    public AddCarDialog() throws IOException {
        super();
        // Load the FXML layout
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("add-car-view.fxml"));
        loader.setController(this);
        setDialogPane(loader.load());
        
        // Configure dialog properties
        setTitle("Add Car");
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        
        // Handle dialog result
        setResultConverter(buttonType -> {
            if (buttonType == ButtonType.APPLY) {
                return new Car(tfMake.getText(),
                        tfModel.getText(),
                        Integer.parseInt(tfYear.getText()),
                        tfColor.getText(),
                        Double.parseDouble(tfMileage.getText()),
                        cbCategory.getValue());
            }
            return null;
        });
    }
}
