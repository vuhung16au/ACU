package com.acu.car.management.controller;

import com.acu.car.management.persistence.repository.UserRepository;
import com.acu.car.management.persistence.model.User;
import com.zaxxer.hikari.HikariDataSource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller for the login screen, handling user authentication
 */
public class LoginController {
    // UI components
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfUsername;
    
    // Database and repository
    private HikariDataSource hikariDataSource;
    private UserRepository userRepository;

    /**
     * Initialize the controller with database connection and setup sample users
     */
    public void initDataSource(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
        this.userRepository = new UserRepository(hikariDataSource);

        // Insert some example users
        // Passwords are saved in "encrypted" form using hashCode
        userRepository.deleteAll();
        userRepository.save(new User("admin", String.valueOf("admin".hashCode())));
        userRepository.save(new User("user", String.valueOf("user".hashCode())));
    }

    /**
     * Handle cancel button click - exit the application
     */
    @FXML
    void onCancelClicked() {
        Platform.exit();
    }

    /**
     * Handle login button click - validate credentials and launch main application
     */
    @FXML
    void onOKClicked() throws IOException {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            if (Objects.equals(user.getUsername(), tfUsername.getText()) &&
                    Objects.equals(user.getPassword(), String.valueOf(tfPassword.getText().hashCode()))) {
                launchApplication();
                return;
            }
        }

        // Show error message for invalid credentials
        Alert a = new Alert(Alert.AlertType.WARNING, "Login Failed");
        a.setHeaderText("Login failed");
        a.setContentText("Username and/or password not valid");
        a.showAndWait();
    }

    /**
     * Launch the main car management application after successful login
     */
    void launchApplication() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cars-view.fxml"));
        Parent root = loader.load();
        OverviewController controller = loader.getController();
        controller.initDataSource(hikariDataSource);

        Stage stage = (Stage) anchorPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Car Management System");
        stage.setScene(scene);
    }
}
