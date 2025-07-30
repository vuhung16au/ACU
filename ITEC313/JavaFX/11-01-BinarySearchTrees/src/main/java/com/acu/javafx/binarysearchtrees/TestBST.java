package com.acu.javafx.binarysearchtrees;

public class TestBST {
  public static void main(String[] args) {
    // Create a BST
    BST<String> tree = new BST<>();
    tree.insert("Green");
    tree.insert("Mabel");
    tree.insert("Teal");
    tree.insert("Blue");
    tree.insert("Jade");
    tree.insert("Purple"); // Insert Purple to the tree
    tree.insert("Danube");

    // Traverse tree
    System.out.print("Inorder (sorted): ");
    tree.inorder();
    System.out.print("\nPostorder: ");
    tree.postorder();
    System.out.print("\nPreorder: ");
    tree.preorder();
    System.out.print("\nThe number of nodes is " + tree.getSize());

    // Search for an element
    System.out.print("\nIs Purple in the tree? " + 
      tree.search("Purple"));

    // Get a path from the root to Purple
    System.out.print("\nA path from the root to Purple is: ");
    java.util.ArrayList<BST.TreeNode<String>> path 
      = tree.path("Purple");
    for (int i = 0; path != null && i < path.size(); i++)
      System.out.print(path.get(i).element + " ");

    Integer[] numbers = {2, 4, 3, 1, 8, 5, 6, 7};
    BST<Integer> intTree = new BST<>(numbers); 
    System.out.print("\nInorder (sorted): ");
    intTree.inorder();
  }
}
