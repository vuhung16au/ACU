/**
 * Stream Gatherers Demo - Java 25 Feature
 * 
 * Stream Gatherers (JEP 461) add gatherers to the Stream API, allowing more flexible
 * and powerful data collection operations beyond traditional collectors.
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * @author Java 25 Features Demo
 * Date: 2025
 */

import java.util.*;
import java.util.stream.*;

public class StreamGatherersDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java 25 Stream Gatherers Demo ===\n");
        
        traditionalCollectors();
        simulateStreamGatherers();
        windowingOperations();
        customGatheringLogic();
        performanceImprovements();
    }
    
    /**
     * Shows traditional stream collectors for comparison
     */
    private static void traditionalCollectors() {
        System.out.println("1. Traditional Stream Collectors:");
        
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Traditional collectors
        List<Integer> evenNumbers = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("Even numbers: " + evenNumbers);
        
        Map<Boolean, List<Integer>> partitioned = numbers.stream()
            .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Partitioned: " + partitioned);
        
        String joined = numbers.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(", "));
        System.out.println("Joined: " + joined);
        
        System.out.println();
    }
    
    /**
     * Simulates Stream Gatherers functionality
     */
    private static void simulateStreamGatherers() {
        System.out.println("2. Stream Gatherers (Java 25 concept):");
        
        List<String> words = List.of("apple", "banana", "cherry", "date", "elderberry", "fig");
        
        // Traditional approach to get every 2nd element
        System.out.println("Traditional approach - every 2nd element:");
        List<String> every2nd = IntStream.range(0, words.size())
            .filter(i -> i % 2 == 0)
            .mapToObj(words::get)
            .collect(Collectors.toList());
        System.out.println(every2nd);
        
        // Simulate gatherer for taking every nth element
        System.out.println("\nGatherer concept - every 2nd element:");
        List<String> gathered = simulateNthElementGatherer(words.stream(), 2)
            .collect(Collectors.toList());
        System.out.println(gathered);
        
        // Simulate sliding window gatherer
        System.out.println("\nGatherer concept - sliding window of size 3:");
        List<List<String>> windows = simulateSlidingWindowGatherer(words.stream(), 3)
            .collect(Collectors.toList());
        windows.forEach(window -> System.out.println("  " + window));
        
        System.out.println();
    }
    
    /**
     * Demonstrates windowing operations with gatherers
     */
    private static void windowingOperations() {
        System.out.println("3. Windowing Operations:");
        
        List<Integer> data = List.of(1, 3, 5, 2, 8, 13, 21, 1, 4, 7, 11, 18);
        
        // Fixed-size windows
        System.out.println("Fixed-size windows (size 4):");
        List<List<Integer>> fixedWindows = simulateFixedWindowGatherer(data.stream(), 4)
            .collect(Collectors.toList());
        fixedWindows.forEach(window -> {
            double avg = window.stream().mapToInt(Integer::intValue).average().orElse(0);
            System.out.println("  " + window + " -> avg: " + String.format("%.1f", avg));
        });
        
        // Tumbling windows based on condition
        System.out.println("\nTumbling windows (split on values > 10):");
        List<List<Integer>> tumblingWindows = simulateTumblingWindowGatherer(data.stream(), n -> n > 10)
            .collect(Collectors.toList());
        tumblingWindows.forEach(window -> System.out.println("  " + window));
        
        System.out.println();
    }
    
    /**
     * Demonstrates custom gathering logic
     */
    private static void customGatheringLogic() {
        System.out.println("4. Custom Gathering Logic:");
        
        List<String> sentences = List.of(
            "Java 25 is great",
            "Stream gatherers are powerful",
            "This feature improves productivity",
            "Code becomes more expressive"
        );
        
        // Custom gatherer to collect words with their positions
        System.out.println("Words with positions:");
        Map<String, List<Integer>> wordPositions = sentences.stream()
            .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
            .collect(simulateWordPositionGatherer());
        
        wordPositions.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> 
                System.out.println("  '" + entry.getKey() + "' at positions: " + entry.getValue()));
        
        // Custom gatherer for running statistics
        System.out.println("\nRunning statistics gatherer:");
        List<Integer> values = List.of(5, 2, 8, 1, 9, 3, 7, 4, 6);
        List<String> runningStats = simulateRunningStatsGatherer(values.stream())
            .collect(Collectors.toList());
        
        runningStats.forEach(System.out::println);
        
        System.out.println();
    }
    
    /**
     * Demonstrates performance improvements with gatherers
     */
    private static void performanceImprovements() {
        System.out.println("5. Performance Improvements:");
        
        // Generate large dataset
        List<Integer> largeDataset = IntStream.range(1, 100_000)
            .boxed()
            .collect(Collectors.toList());
        
        // Traditional approach
        long startTime = System.nanoTime();
        List<List<Integer>> traditionalBatches = new ArrayList<>();
        for (int i = 0; i < largeDataset.size(); i += 1000) {
            traditionalBatches.add(largeDataset.subList(i, Math.min(i + 1000, largeDataset.size())));
        }
        long traditionalTime = System.nanoTime() - startTime;
        
        // Gatherer approach (simulated)
        startTime = System.nanoTime();
        List<List<Integer>> gathererBatches = simulateFixedWindowGatherer(largeDataset.stream(), 1000)
            .collect(Collectors.toList());
        long gathererTime = System.nanoTime() - startTime;
        
        System.out.println("Processing " + largeDataset.size() + " elements:");
        System.out.println("Traditional batching: " + String.format("%.2f ms", traditionalTime / 1_000_000.0));
        System.out.println("Gatherer batching:    " + String.format("%.2f ms", gathererTime / 1_000_000.0));
        System.out.println("Batches created: " + gathererBatches.size());
        
        // Memory efficiency demonstration
        System.out.println("\nMemory efficiency with lazy evaluation:");
        long count = largeDataset.stream()
            .filter(n -> n % 2 == 0)
            .limit(10)  // Gatherers would be more efficient for this
            .count();
        System.out.println("Found " + count + " even numbers (stopped early with gatherer optimization)");
        
        System.out.println();
    }
    
    // Simulation methods for gatherer concepts
    
    private static <T> Stream<T> simulateNthElementGatherer(Stream<T> stream, int n) {
        List<T> list = stream.collect(Collectors.toList());
        return IntStream.range(0, list.size())
            .filter(i -> i % n == 0)
            .mapToObj(list::get);
    }
    
    private static <T> Stream<List<T>> simulateSlidingWindowGatherer(Stream<T> stream, int windowSize) {
        List<T> list = stream.collect(Collectors.toList());
        return IntStream.range(0, Math.max(0, list.size() - windowSize + 1))
            .mapToObj(i -> list.subList(i, i + windowSize));
    }
    
    private static <T> Stream<List<T>> simulateFixedWindowGatherer(Stream<T> stream, int windowSize) {
        List<T> list = stream.collect(Collectors.toList());
        List<List<T>> windows = new ArrayList<>();
        for (int i = 0; i < list.size(); i += windowSize) {
            windows.add(list.subList(i, Math.min(i + windowSize, list.size())));
        }
        return windows.stream();
    }
    
    private static <T> Stream<List<T>> simulateTumblingWindowGatherer(Stream<T> stream, 
                                                                      java.util.function.Predicate<T> splitCondition) {
        List<T> list = stream.collect(Collectors.toList());
        List<List<T>> windows = new ArrayList<>();
        List<T> currentWindow = new ArrayList<>();
        
        for (T item : list) {
            if (splitCondition.test(item) && !currentWindow.isEmpty()) {
                windows.add(new ArrayList<>(currentWindow));
                currentWindow.clear();
            }
            currentWindow.add(item);
        }
        
        if (!currentWindow.isEmpty()) {
            windows.add(currentWindow);
        }
        
        return windows.stream();
    }
    
    private static Collector<String, ?, Map<String, List<Integer>>> simulateWordPositionGatherer() {
        return Collector.of(
            HashMap::new,
            (map, word) -> {
                map.computeIfAbsent(word, k -> new ArrayList<>()).add(map.size());
            },
            (map1, map2) -> {
                map2.forEach((key, value) -> map1.merge(key, value, (v1, v2) -> {
                    List<Integer> combined = new ArrayList<>(v1);
                    combined.addAll(v2);
                    return combined;
                }));
                return map1;
            }
        );
    }
    
    private static Stream<String> simulateRunningStatsGatherer(Stream<Integer> stream) {
        List<Integer> list = stream.collect(Collectors.toList());
        List<String> results = new ArrayList<>();
        
        int sum = 0;
        int count = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        for (Integer value : list) {
            sum += value;
            count++;
            min = Math.min(min, value);
            max = Math.max(max, value);
            
            double avg = (double) sum / count;
            results.add(String.format("After %d values: sum=%d, avg=%.1f, min=%d, max=%d", 
                                     count, sum, avg, min, max));
        }
        
        return results.stream();
    }
}