module com.acu.javafx.bouncingball {
    requires transitive javafx.graphics; // Application & Stage are exposed in API
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    exports com.acu.javafx.bouncingball;
} 