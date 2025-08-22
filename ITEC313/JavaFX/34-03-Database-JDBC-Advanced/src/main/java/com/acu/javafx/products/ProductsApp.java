package com.acu.javafx.products;

import com.acu.javafx.products.controller.OverviewController;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX entrypoint for the Products application.
 * <p>
 * Responsible for initializing the JDBC connection pool (HikariCP),
 * loading the main FXML view, wiring the controller with the datasource,
 * and showing the primary stage.
 */
public class ProductsApp extends Application {
    private static final String JDBC_Driver = "org.postgresql.Driver";
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/jdbc_advanced?user=postgres&password=postgres&ssl=false";

    /**
     * Application main entry point.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    /**
     * Initializes the scene graph and controller, then displays the UI.
     */
    public void start(Stage primaryStage) throws IOException {
        HikariDataSource hikariDataSource = initDataSource(JDBC_Driver, JDBC_URL);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("products-view.fxml"));
        Parent root = loader.load();
        OverviewController controller = loader.getController();
        controller.initDataSource(hikariDataSource);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Products");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates and configures a HikariCP datasource for PostgreSQL.
     */
    private HikariDataSource initDataSource(String JDBC_Driver, String JDBC_URL) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(JDBC_Driver);
        config.setJdbcUrl(JDBC_URL);
        config.setLeakDetectionThreshold(2000);
        return new HikariDataSource(config);
    }
}
