package com.acu.javafx.externalsort;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

/**
 * Utility to create a large test file of integers, one per line.
 * Kept very simple for teaching purposes.
 */
public final class CreateLargeFile {
    private CreateLargeFile() { }

    /**
     * Writes {@code count} random integers to {@code targetFile}, one per line.
     */
    public static void generateRandomIntegersFile(Path targetFile, int count) throws IOException {
        Files.createDirectories(targetFile.getParent() == null ? Path.of(".") : targetFile.getParent());
        Random random = new Random(42L); // fixed seed for reproducibility
        try (BufferedWriter writer = Files.newBufferedWriter(targetFile, StandardCharsets.UTF_8)) {
            for (int i = 0; i < count; i++) {
                int value = random.nextInt(Integer.MAX_VALUE);
                writer.write(Integer.toString(value));
                writer.newLine();
            }
        }
    }
}


