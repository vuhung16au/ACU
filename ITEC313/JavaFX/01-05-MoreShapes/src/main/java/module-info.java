module com.example.moreshapes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    exports com.example;
    opens com.example to javafx.fxml;
} 