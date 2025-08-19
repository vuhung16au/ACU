module com.acu.javafx.database {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens com.acu.javafx.database to javafx.fxml;
    exports com.acu.javafx.database;
}
