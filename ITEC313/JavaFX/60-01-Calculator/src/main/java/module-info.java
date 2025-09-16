module com.acu.javafx.calculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;

    opens com.acu.javafx.calculator.controllers to javafx.fxml;
    exports com.acu.javafx.calculator;
}