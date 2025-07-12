module javafx.button.demo {
    requires javafx.controls;
    requires javafx.fxml;
    
    exports com.acu.javafx;
    
    // Opens the controller package to javafx.fxml for reflection access
    // This is required for @FXML field injection
    opens com.acu.javafx.controller to javafx.fxml;
}
