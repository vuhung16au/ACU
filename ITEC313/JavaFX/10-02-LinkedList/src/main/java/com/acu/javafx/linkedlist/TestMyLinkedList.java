package com.acu.javafx.linkedlist;

public class TestMyLinkedList {
  public static void main(String[] args) {
    MyLinkedList<String> list = new MyLinkedList<>();

    list.add("America");
    System.out.println("(1) " + list);

    list.add(0, "Canada");
    System.out.println("(2) " + list);

    list.add("Russia");
    System.out.println("(3) " + list);

    list.addLast("France");
    System.out.println("(4) " + list);

    list.add(2, "Germany");
    System.out.println("(5) " + list);

    list.add(5, "Norway");
    System.out.println("(6) " + list);

    list.add(0, "Poland");
    System.out.println("(7) " + list);

    list.remove(0);
    System.out.println("(8) " + list);

    list.remove(2);
    System.out.println("(9) " + list);

    list.remove(list.size() - 1);
    System.out.print("(10) " + list + "\n(11) ");
    
    for (String s: list)
      System.out.print(s.toUpperCase() + " ");
    
    list.clear();
    System.out.println("\nAfter clearing the list, the list size is " 
      + list.size());
  }
}
