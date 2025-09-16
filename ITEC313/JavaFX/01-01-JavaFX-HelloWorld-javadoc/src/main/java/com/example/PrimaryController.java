package com.example;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 * Controller for the primary view.
 * <p>
 * Handles user interactions on the primary screen and navigates to the
 * secondary view.
 */
public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        /**
         * Action handler that switches from the primary view to the secondary view.
         *
         * @throws IOException if the FXML cannot be loaded
         */
        App.setRoot("secondary");
    }
}
