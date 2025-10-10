package com.acu.javafx.directory;

/**
 * JavaFX Launcher class to avoid classpath issues with certain build tools.
 */
public final class Launcher {
    private Launcher() { }
    public static void main(String[] args) {
        DirectorySizeApp.main(args);
    }
}


