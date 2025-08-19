package com.example;

/**
 * MultiPlayerLauncher provides a way to start either the server or client
 * based on command line arguments.
 * 
 * Usage:
 * - To start server: java -cp . com.example.MultiPlayerLauncher server
 * - To start client: java -cp . com.example.MultiPlayerLauncher client
 * - Default: starts the server
 * 
 * @author ITEC313 Student
 * @version 3.0.0
 */
public class MultiPlayerLauncher {
    
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("client")) {
            System.out.println("Starting TicTacToe Client...");
            TicTacToeClient.main(args);
        } else {
            System.out.println("Starting TicTacToe Server...");
            System.out.println("Server will listen on port 8000");
            System.out.println("To start a client, run: java -cp . com.example.MultiPlayerLauncher client");
            TicTacToeServer.main(args);
        }
    }
}
