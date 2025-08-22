/**
 * Java module descriptor for the Products JavaFX application.
 */
open module com.acu.javafx.products {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    // for database connection pooling
    requires com.zaxxer.hikari; 

    // Required by HikariCP for logging
    requires org.slf4j; 
}