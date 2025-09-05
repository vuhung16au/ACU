package com.acu.javafx.recursion;

import org.apache.commons.cli.*;
import java.io.File;

/**
 * DirectorySizeWithOptions - A CLI application to calculate directory size with command-line options
 * 
 * Usage:
 *   java DirectorySizeWithOptions -d <directory> [-v]
 *   java DirectorySizeWithOptions --directory <directory> [--verbose]
 * 
 * Options:
 *   -d, --directory    Specify the input directory (required)
 *   -v, --verbose      Show verbose output in console
 *   -h, --help         Show help information
 */
public class DirectorySizeWithOptions {
    
    public static void main(String[] args) {
        // --- 1. Definition Stage: Define the command-line options ---
        Options options = new Options();

        // Define a required option with a short name (-d) and a long name (--directory)
        Option directory = new Option("d", "directory", true, "Input directory path");
        directory.setRequired(true);
        options.addOption(directory);

        // Define a boolean option (flag) for verbose output
        Option verbose = new Option("v", "verbose", false, "Enable verbose output");
        options.addOption(verbose);
        
        // Define help option
        Option help = new Option("h", "help", false, "Show help information");
        options.addOption(help);

        // Check for help option first (before parsing)
        for (String arg : args) {
            if (arg.equals("-h") || arg.equals("--help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("DirectorySizeWithOptions", options);
                System.exit(0);
            }
        }

        // --- 2. Parsing Stage: Parse the command-line arguments ---
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Error: " + e.getMessage());
            formatter.printHelp("DirectorySizeWithOptions", options);
            System.exit(1);
            return;
        }

        // --- 3. Interrogation Stage: Use the parsed options ---
        String directoryPath = cmd.getOptionValue("directory");
        boolean isVerbose = cmd.hasOption("verbose");

        // Validate that the directory exists
        File targetDirectory = new File(directoryPath);
        if (!targetDirectory.exists()) {
            System.err.println("Error: Directory '" + directoryPath + "' does not exist.");
            System.exit(1);
        }

        if (!targetDirectory.isDirectory()) {
            System.err.println("Error: '" + directoryPath + "' is not a directory.");
            System.exit(1);
        }

        if (isVerbose) {
            System.out.println("Calculating size for directory: " + directoryPath);
            System.out.println("Verbose mode is enabled.");
            System.out.println("----------------------------------------");
        }

        // Calculate and display the size
        long size = getSize(targetDirectory, isVerbose);
        
        if (isVerbose) {
            System.out.println("----------------------------------------");
        }
        
        System.out.println("Total size: " + formatBytes(size) + " (" + size + " bytes)");
    }

    /**
     * Recursively calculates the size of a file or directory
     * @param file the file or directory to calculate size for
     * @param verbose whether to show verbose output
     * @return the total size in bytes
     */
    public static long getSize(File file, boolean verbose) {
        long size = 0; // Store the total size of all files

        if (file.isDirectory()) {
            if (verbose) {
                System.out.println("Scanning directory: " + file.getAbsolutePath());
            }
            
            File[] files = file.listFiles(); // All files and subdirectories
            if (files != null) {
                for (File subFile : files) {
                    size += getSize(subFile, verbose); // Recursive call
                }
            }
        } else { // Base case
            size = file.length();
            if (verbose) {
                System.out.println("File: " + file.getName() + " - " + formatBytes(size));
            }
        }

        return size;
    }

    /**
     * Formats bytes into human-readable format
     * @param bytes the number of bytes
     * @return formatted string with appropriate unit
     */
    private static String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
        }
    }
}
