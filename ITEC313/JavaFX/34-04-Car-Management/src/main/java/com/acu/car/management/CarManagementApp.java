package com.acu.car.management;

import com.acu.car.management.controller.LoginController;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Main application class for the Car Management System.
 * Handles application startup, database connection, and initial UI setup.
 */
public class CarManagementApp extends Application {
    // Database connection configuration
    private static final String JDBC_Driver = "org.postgresql.Driver";
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/car_management?user=postgres&password=postgres&ssl=false";
    
    // Available car categories for the application
    public static final List<String> carTypes = List.of("Sedan", "SUV", "Hatchback", "Coupe", "Convertible", "Wagon", "Truck", "Van");

    /**
     * Application entry point
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the application UI and database connection
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initialize database connection pool
        HikariDataSource hikariDataSource = initDataSource(JDBC_Driver, JDBC_URL);

        // Load login view and setup controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("controller/login-view.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.initDataSource(hikariDataSource);

        // Setup and display the main window
        Scene scene = new Scene(root);
        primaryStage.setTitle("Car Management - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates and configures the HikariCP database connection pool
     */
    private HikariDataSource initDataSource(String JDBC_Driver, String JDBC_URL) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(JDBC_Driver);
        config.setJdbcUrl(JDBC_URL);
        config.setLeakDetectionThreshold(2000);
        return new HikariDataSource(config);
    }
}
