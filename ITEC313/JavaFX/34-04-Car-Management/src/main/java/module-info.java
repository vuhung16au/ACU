// Module declaration for the Car Management application
// This module is declared as "open" to allow reflection access for JavaFX FXML loading
open module com.acu.car.management {
    
    // Required for database connectivity and SQL operations
    // Used by repository classes to interact with the database
    requires java.sql;

    // Required for JavaFX UI components (buttons, labels, tables, etc.)
    // Provides the core UI controls used throughout the application
    requires javafx.controls;
    
    // Required for FXML file loading and controller binding
    // Enables the application to load UI layouts from FXML files and bind them to controllers
    requires javafx.fxml;
    
    // Required for connection pooling with HikariCP
    // Provides efficient database connection management for better performance
    requires com.zaxxer.hikari;

    // Required for logging functionality
    // Provides a logging facade that can be configured with different logging implementations
    requires org.slf4j;
}