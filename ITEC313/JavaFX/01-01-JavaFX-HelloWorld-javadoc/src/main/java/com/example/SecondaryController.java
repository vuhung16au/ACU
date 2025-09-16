package com.example;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 * Controller for the secondary view.
 * <p>
 * Handles user interactions on the secondary screen and navigates back to the
 * primary view.
 */
public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        /**
         * Action handler that switches from the secondary view back to the primary view.
         *
         * @throws IOException if the FXML cannot be loaded
         */
        App.setRoot("primary");
    }
}