package com.acu.javafx.priorityqueue;

import java.util.*;

public class PriorityQueueDemo {
  public static void main(String[] args) {
    PriorityQueue<String> queue1 = new PriorityQueue<>();
    queue1.offer("Sydney");
    queue1.offer("Melbourne");
    queue1.offer("Brisbane");
    queue1.offer("Perth");

    System.out.println("Priority queue using Comparable:");
    while (queue1.size() > 0) {
      System.out.print(queue1.remove() + " ");
    }

    PriorityQueue<String> queue2 = new PriorityQueue<>(
      4, Collections.reverseOrder());
    queue2.offer("Sydney");
    queue2.offer("Melbourne");
    queue2.offer("Brisbane");
    queue2.offer("Perth");

    System.out.println("\nPriority queue using Comparator:");
    while (queue2.size() > 0) {
      System.out.print(queue2.remove() + " ");
    }
  }
} 