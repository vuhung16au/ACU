package com.acu.javafx.binarysearchtrees;

public class TestBSTWithIterator {
  public static void main(String[] args) {
    BST<String> tree = new BST<>();
    tree.insert("Green");
    tree.insert("Mabel");
    tree.insert("Teal");
    tree.insert("Blue");
    tree.insert("Jade");
    tree.insert("Purple");
    tree.insert("Danube");
    
    for (String s: tree) // Use a foreach loop
      System.out.print(s.toUpperCase() + " ");
  }
}
