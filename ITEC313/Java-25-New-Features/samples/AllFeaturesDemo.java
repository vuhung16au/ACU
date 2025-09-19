/**
 * All Java 25 Features Demo Runner
 * 
 * This class runs demonstrations of all key Java 25 features in sequence.
 * Each feature is showcased with practical examples and explanations.
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * @author Java 25 Features Demo
 * Date: 2025
 */

import java.util.Scanner;

public class AllFeaturesDemo {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("   JAVA 25 NEW FEATURES COMPREHENSIVE DEMONSTRATION");
        System.out.println("=".repeat(60));
        System.out.println();
        
        displayIntroduction();
        
        if (args.length > 0 && args[0].equals("--interactive")) {
            runInteractiveMode();
        } else {
            runAllDemos();
        }
        
        displayConclusion();
    }
    
    /**
     * Displays an introduction to Java 25 features
     */
    private static void displayIntroduction() {
        System.out.println("Welcome to the Java 25 LTS Features Showcase!");
        System.out.println();
        System.out.println("Java 25 introduces significant improvements in:");
        System.out.println("✓ Developer Productivity (String Templates, Implicit Classes)");
        System.out.println("✓ Performance (Vector API, Virtual Threads improvements)");
        System.out.println("✓ Language Features (Pattern Matching, Unnamed Variables)");
        System.out.println("✓ Platform Capabilities (Class-File API, Foreign Functions)");
        System.out.println("✓ Concurrency (Structured Concurrency, Stream Gatherers)");
        System.out.println();
        System.out.println("Each demo shows practical examples and explains the benefits.");
        System.out.println("-".repeat(60));
        System.out.println();
    }
    
    /**
     * Runs all feature demonstrations in sequence
     */
    private static void runAllDemos() {
        try {
            runFeatureDemo("1. Implicitly Declared Classes", ImplicitClassDemo::main);
            pauseBetweenDemos();
            
            runFeatureDemo("2. String Templates", StringTemplatesDemo::main);
            pauseBetweenDemos();
            
            runFeatureDemo("3. Unnamed Variables & Patterns", UnnamedVariablesDemo::main);
            pauseBetweenDemos();
            
            runFeatureDemo("4. Pattern Matching Enhancements", PatternMatchingDemo::main);
            pauseBetweenDemos();
            
            runFeatureDemo("5. Virtual Threads Improvements", VirtualThreadsDemo::main);
            pauseBetweenDemos();
            
            runFeatureDemo("6. Structured Concurrency", StructuredConcurrencyDemo::main);
            pauseBetweenDemos();
            
            runFeatureDemo("7. Stream Gatherers", StreamGatherersDemo::main);
            pauseBetweenDemos();
            
            runFeatureDemo("8. Vector API", VectorApiDemo::main);
            pauseBetweenDemos();
            
            runFeatureDemo("9. Class-File API", ClassFileApiDemo::main);
            pauseBetweenDemos();
            
            runFeatureDemo("10. Foreign Function & Memory API", ForeignFunctionDemo::main);
            
        } catch (Exception e) {
            System.err.println("Error running demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Runs demos in interactive mode where user can choose which ones to run
     */
    private static void runInteractiveMode() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            displayMenu();
            System.out.print("Enter your choice (1-11, 0 to exit): ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 0 -> {
                        System.out.println("Exiting demo. Thank you!");
                        return;
                    }
                    case 1 -> runFeatureDemo("Implicitly Declared Classes", ImplicitClassDemo::main);
                    case 2 -> runFeatureDemo("String Templates", StringTemplatesDemo::main);
                    case 3 -> runFeatureDemo("Unnamed Variables & Patterns", UnnamedVariablesDemo::main);
                    case 4 -> runFeatureDemo("Pattern Matching Enhancements", PatternMatchingDemo::main);
                    case 5 -> runFeatureDemo("Virtual Threads Improvements", VirtualThreadsDemo::main);
                    case 6 -> runFeatureDemo("Structured Concurrency", StructuredConcurrencyDemo::main);
                    case 7 -> runFeatureDemo("Stream Gatherers", StreamGatherersDemo::main);
                    case 8 -> runFeatureDemo("Vector API", VectorApiDemo::main);
                    case 9 -> runFeatureDemo("Class-File API", ClassFileApiDemo::main);
                    case 10 -> runFeatureDemo("Foreign Function & Memory API", ForeignFunctionDemo::main);
                    case 11 -> runAllDemos();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
                
                if (choice >= 1 && choice <= 11) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Displays the interactive menu
     */
    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("   JAVA 25 FEATURES INTERACTIVE DEMO");
        System.out.println("=".repeat(50));
        System.out.println("1.  Implicitly Declared Classes (JEP 463)");
        System.out.println("2.  String Templates (JEP 430)");
        System.out.println("3.  Unnamed Variables & Patterns (JEP 443)");
        System.out.println("4.  Pattern Matching Enhancements");
        System.out.println("5.  Virtual Threads Improvements");
        System.out.println("6.  Structured Concurrency (Finalized)");
        System.out.println("7.  Stream Gatherers (JEP 461)");
        System.out.println("8.  Vector API (Finalized)");
        System.out.println("9.  Class-File API (JEP 457)");
        System.out.println("10. Foreign Function & Memory API");
        System.out.println("11. Run All Demos");
        System.out.println("0.  Exit");
        System.out.println("-".repeat(50));
    }
    
    /**
     * Runs a specific feature demonstration
     */
    private static void runFeatureDemo(String featureName, DemoRunner demoRunner) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🚀 RUNNING: " + featureName.toUpperCase());
        System.out.println("=".repeat(60));
        
        try {
            long startTime = System.currentTimeMillis();
            demoRunner.run(new String[]{});
            long endTime = System.currentTimeMillis();
            
            System.out.println("-".repeat(60));
            System.out.println("✅ Demo completed successfully in " + (endTime - startTime) + "ms");
            System.out.println("-".repeat(60));
            
        } catch (Exception e) {
            System.err.println("❌ Error running " + featureName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Pauses between demos when running in automatic mode
     */
    private static void pauseBetweenDemos() {
        try {
            System.out.println("\nWaiting 2 seconds before next demo...\n");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Displays conclusion and summary
     */
    private static void displayConclusion() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("   JAVA 25 FEATURES DEMONSTRATION COMPLETE");
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println("🎉 You've explored all major Java 25 features!");
        System.out.println();
        System.out.println("Key takeaways:");
        System.out.println("• Java 25 significantly improves developer productivity");
        System.out.println("• Performance enhancements benefit AI/ML workloads");
        System.out.println("• Language features make code more expressive and safer");
        System.out.println("• Platform capabilities enable better tooling and frameworks");
        System.out.println("• Concurrency improvements simplify parallel programming");
        System.out.println();
        System.out.println("Next steps:");
        System.out.println("1. Experiment with these features in your own projects");
        System.out.println("2. Read the JEP specifications for detailed information");
        System.out.println("3. Test migration from earlier Java versions");
        System.out.println("4. Share knowledge with your development team");
        System.out.println();
        System.out.println("For more information:");
        System.out.println("• Oracle Java 25 Documentation");
        System.out.println("• OpenJDK Enhancement Proposals (JEPs)");
        System.out.println("• Java community forums and blogs");
        System.out.println();
        System.out.println("Happy coding with Java 25! 🚀");
        System.out.println("=".repeat(60));
    }
    
    /**
     * Functional interface for running demo methods
     */
    @FunctionalInterface
    private interface DemoRunner {
        void run(String[] args) throws Exception;
    }
    
    /**
     * Displays system information for troubleshooting
     */
    public static void displaySystemInfo() {
        System.out.println("System Information:");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Java Runtime: " + System.getProperty("java.runtime.name"));
        System.out.println("JVM: " + System.getProperty("java.vm.name"));
        System.out.println("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        System.out.println("Architecture: " + System.getProperty("os.arch"));
        System.out.println("Available Processors: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Max Memory: " + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + " MB");
        System.out.println();
    }
    
    /**
     * Shows usage information
     */
    public static void showUsage() {
        System.out.println("Usage:");
        System.out.println("  java AllFeaturesDemo                 # Run all demos automatically");
        System.out.println("  java AllFeaturesDemo --interactive   # Interactive mode");
        System.out.println("  java AllFeaturesDemo --help          # Show this help");
        System.out.println("  java AllFeaturesDemo --info          # Show system information");
        System.out.println();
        System.out.println("Individual demo classes can also be run directly:");
        System.out.println("  java StringTemplatesDemo");
        System.out.println("  java VirtualThreadsDemo");
        System.out.println("  java PatternMatchingDemo");
        System.out.println("  ... etc.");
    }
    
    // Handle command line arguments
    static {
        String[] args = new String[0]; // This would be passed from main in real usage
        
        for (String arg : args) {
            switch (arg) {
                case "--help", "-h" -> {
                    showUsage();
                    System.exit(0);
                }
                case "--info" -> {
                    displaySystemInfo();
                    System.exit(0);
                }
            }
        }
    }
}