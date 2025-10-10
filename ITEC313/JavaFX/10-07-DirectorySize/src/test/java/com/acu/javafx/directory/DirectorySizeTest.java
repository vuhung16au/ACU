package com.acu.javafx.directory;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectorySizeTest {

    @Test
    void computesSizeForNestedStructureWithoutRecursion() throws Exception {
        Path tmp = Files.createTempDirectory("dirsize-test");
        try {
            // Create nested directories and files
            Path a = Files.createDirectory(tmp.resolve("a"));
            Path b = Files.createDirectory(a.resolve("b"));
            Path c = Files.createDirectory(tmp.resolve("c"));

            // Write small files with known sizes
            writeBytes(tmp.resolve("root.bin"), 10);
            writeBytes(a.resolve("a.dat"), 5);
            writeBytes(b.resolve("b.dat"), 7);
            writeBytes(c.resolve("c.dat"), 3);

            long expected = 10 + 5 + 7 + 3;
            long actual = DirectorySize.computeSize(tmp.toFile());
            assertEquals(expected, actual);
        } finally {
            // Cleanup
            deleteRecursively(tmp.toFile());
        }
    }

    private static void writeBytes(Path path, int count) throws Exception {
        try (FileOutputStream out = new FileOutputStream(path.toFile())) {
            out.write(new byte[count]);
        }
    }

    private static void deleteRecursively(File file) {
        if (file == null || !file.exists()) return;
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File f : children) {
                    deleteRecursively(f);
                }
            }
        }
        assertTrue(file.delete());
    }
}


