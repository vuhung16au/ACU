package com.acu.javafx.externalsort;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExternalSortTest {
    @Test
    void sortSmallFile() throws IOException {
        Path input = Files.createTempFile("extsort-input-", ".txt");
        Path output = Files.createTempFile("extsort-output-", ".txt");
        Files.write(input, List.of("5", "3", "9", "1", "7"));

        SortLargeFile.externalSort(input, output, 2);

        List<String> lines = Files.readAllLines(output);
        assertEquals(List.of("1", "3", "5", "7", "9"), lines);
    }
}


