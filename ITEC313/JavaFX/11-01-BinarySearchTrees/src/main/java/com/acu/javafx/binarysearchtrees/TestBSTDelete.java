package com.acu.javafx.binarysearchtrees;

public class TestBSTDelete {
  public static void main(String[] args) {
    BST<String> tree = new BST<>();
    tree.insert("Green");
    tree.insert("Mabel");
    tree.insert("Teal");
    tree.insert("Blue");
    tree.insert("Jade");
    tree.insert("Purple");
    tree.insert("Danube");
    printTree(tree);

    System.out.println("\nAfter delete Green:");
    tree.delete("Green");
    printTree(tree);

    System.out.println("\nAfter delete Blue:");
    tree.delete("Blue"); // Delete Blue from the tree
    printTree(tree);

    System.out.println("\nAfter delete Mabel:");
    tree.delete("Mabel");
    printTree(tree);
  }

  public static void printTree(BST<String> tree) {
    // Traverse tree
    System.out.print("Inorder (sorted): ");
    tree.inorder();
    System.out.print("\nPostorder: ");
    tree.postorder();
    System.out.print("\nPreorder: ");
    tree.preorder();
    System.out.print("\nThe number of nodes is " + tree.size());
    System.out.println();
  }
}
