module com.acu.javafx.binaryio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    
    exports com.acu.javafx.binaryio;
    
    opens com.acu.javafx.binaryio to javafx.fxml;
} 