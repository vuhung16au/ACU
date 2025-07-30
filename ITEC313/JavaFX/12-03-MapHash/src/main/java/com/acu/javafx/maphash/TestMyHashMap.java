package com.acu.javafx.maphash;

public class TestMyHashMap {
  public static void main(String[] args) {
    // Create a map
    MyMap<String, Integer> map = new MyHashMap<>();
    map.put("Perez", 30); // Add Perez with age 30 to map
    map.put("Anderson", 31);
    map.put("Lewis", 29);
    map.put("Cook", 29);
    map.put("Perez", 65); // Add Perez with age 65 to map

    System.out.println("Entries in map: " + map);

    System.out.println("The age for " + "Lewis is " +
      map.get("Lewis"));

    System.out.println("Is Perez in the map? " + 
      map.containsKey("Perez"));
    System.out.println("Is age 33 in the map? " + 
      map.containsValue(33));

    map.remove("Smith"); // Remove Perez from map
    System.out.println("Entries in map: " + map);

    map.clear();
    System.out.println("Entries in map: " + map);
  }
} 