module com.acu.javafx.fileclass {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    
    exports com.acu.javafx.fileclass;
    exports com.acu.javafx.fileclass.demo;
    
    opens com.acu.javafx.fileclass to javafx.fxml;
    opens com.acu.javafx.fileclass.demo to javafx.fxml;
} 