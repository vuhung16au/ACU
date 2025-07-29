module com.acu.javafx.shape {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    exports com.acu.javafx.shape;
    opens com.acu.javafx.shape to javafx.fxml;
} 