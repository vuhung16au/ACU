package com.acu.javafx.externalsort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * A simple external sort for integer lines.
 *
 * Algorithm (two-phase):
 * 1) Split input into sorted runs: read up to chunkSize lines, parse to integers, sort in-memory, write to a temp file.
 * 2) K-way merge the run files using a min-heap into the final output.
 */
public final class SortLargeFile {
    private SortLargeFile() { }

    public static void externalSort(Path input, Path output, int chunkSize) throws IOException {
        List<Path> runs = createSortedRuns(input, chunkSize);
        kWayMerge(runs, output);
        // Cleanup temp run files
        for (Path run : runs) {
            try { Files.deleteIfExists(run); } catch (IOException ignored) { }
        }
    }

    private static List<Path> createSortedRuns(Path input, int chunkSize) throws IOException {
        List<Path> runFiles = new ArrayList<>();
        List<Integer> buffer = new ArrayList<>(chunkSize);
        Files.createDirectories(Path.of("data"));

        try (BufferedReader reader = Files.newBufferedReader(input, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    buffer.add(Integer.parseInt(line.trim()));
                }
                if (buffer.size() >= chunkSize) {
                    runFiles.add(writeSortedRun(buffer));
                    buffer.clear();
                }
            }
        }
        if (!buffer.isEmpty()) {
            runFiles.add(writeSortedRun(buffer));
            buffer.clear();
        }
        return runFiles;
    }

    private static Path writeSortedRun(List<Integer> buffer) throws IOException {
        buffer.sort(Integer::compareTo);
        Path run = Files.createTempFile("run-", ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(run, StandardCharsets.UTF_8)) {
            for (Integer v : buffer) {
                writer.write(Integer.toString(v));
                writer.newLine();
            }
        }
        return run;
    }

    private static void kWayMerge(List<Path> runs, Path output) throws IOException {
        class Node implements Comparable<Node> {
            final int value;
            final int runIndex;
            Node(int value, int runIndex) { this.value = value; this.runIndex = runIndex; }
            public int compareTo(Node other) { return Integer.compare(this.value, other.value); }
        }

        List<BufferedReader> readers = new ArrayList<>();
        for (Path run : runs) {
            readers.add(Files.newBufferedReader(run, StandardCharsets.UTF_8));
        }

        PriorityQueue<Node> minHeap = new PriorityQueue<>();
        // Initialize heap with first element of each run
        for (int i = 0; i < readers.size(); i++) {
            String line = readers.get(i).readLine();
            if (line != null) {
                minHeap.add(new Node(Integer.parseInt(line.trim()), i));
            }
        }

        Files.createDirectories(output.getParent() == null ? Path.of(".") : output.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) {
            while (!minHeap.isEmpty()) {
                Node n = minHeap.poll();
                writer.write(Integer.toString(n.value));
                writer.newLine();
                String line = readers.get(n.runIndex).readLine();
                if (line != null) {
                    minHeap.add(new Node(Integer.parseInt(line.trim()), n.runIndex));
                }
            }
        }

        for (BufferedReader r : readers) {
            try { r.close(); } catch (IOException ignored) { }
        }
    }
}


