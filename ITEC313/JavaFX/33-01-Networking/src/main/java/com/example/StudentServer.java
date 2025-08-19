package com.example;

import java.io.*;
import java.net.*;

public class StudentServer {
    private ObjectOutputStream outputToFile;
    private ObjectInputStream inputFromClient;

    public static void main(String[] args) {
        new StudentServer();
    }

    public StudentServer() {
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server started on port 8000");

            // Create an object output stream
            outputToFile = new ObjectOutputStream(
                    new FileOutputStream("student.dat", true));

            while (true) {
                // Listen for a new connection request
                Socket socket = serverSocket.accept();
                System.out.println("Client connected from: " + socket.getInetAddress().getHostAddress());

                // Create an input stream from the socket
                inputFromClient =
                        new ObjectInputStream(socket.getInputStream());

                // Read from input
                Object object = inputFromClient.readObject();

                // Write to the file
                outputToFile.writeObject(object);
                System.out.println("A new student object is stored: " + object);

                // Close the client socket
                socket.close();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (inputFromClient != null) {
                    inputFromClient.close();
                }
                if (outputToFile != null) {
                    outputToFile.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
