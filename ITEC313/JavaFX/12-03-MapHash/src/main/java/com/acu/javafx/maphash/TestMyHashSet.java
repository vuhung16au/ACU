package com.acu.javafx.maphash;

public class TestMyHashSet {
  public static void main(String[] args) {
    // Create a MyHashSet
    java.util.Collection<String> set = new MyHashSet<>();
    set.add("Perez"); // Add Perez to set
    set.add("Anderson");
    set.add("Lewis");
    set.add("Cook");
    set.add("Perez"); // Add Perez to set

    System.out.println("Elements in set: " + set);
    System.out.println("Number of elements in set: " + set.size());
    System.out.println("Is Perez in set? " + set.contains("Perez"));

    set.remove("Perez");
    System.out.print("Names in set in uppercase are ");
    for (String s: set)
      System.out.print(s.toUpperCase() + " ");

    set.clear();
    System.out.println("\nElements in set: " + set);
  }
} 