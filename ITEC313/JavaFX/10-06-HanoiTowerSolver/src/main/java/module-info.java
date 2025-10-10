module com.acu.javafx.hanoi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    exports com.acu.javafx.hanoi;
    opens com.acu.javafx.hanoi to javafx.fxml;
}


