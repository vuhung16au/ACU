# Chapter 13: Binary Search Trees (BST)

## Introduction
This chapter introduces binary search trees (BSTs), a special kind of binary tree where each node's left child is less and right child is greater. You will learn how to insert, delete, and traverse BSTs, and why they are important for fast searching and sorting.

## Algorithms Implemented

### 1. BST Node Class
Defines a node with a value and references to left and right children.
```python
class BSTNode:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None
```

### 2. Insertion in BST
Adds a value to the BST while maintaining the BST property.
```python
def insert_bst(root, value):
    if root is None:
        return BSTNode(value)
    if value < root.value:
        root.left = insert_bst(root.left, value)
    elif value > root.value:
        root.right = insert_bst(root.right, value)
    return root
```

### 3. Inorder Traversal
Prints values in sorted order.
```python
def inorder_traversal(node):
    if node:
        inorder_traversal(node.left)
        print(node.value, end=" ")
        inorder_traversal(node.right)
```

### 4. Deletion in BST
Removes a value from the BST while maintaining the BST property.
```python
def delete_bst(root, value):
    if root is None:
        return None
    if value < root.value:
        root.left = delete_bst(root.left, value)
    elif value > root.value:
        root.right = delete_bst(root.right, value)
    else:
        if root.left is None:
            return root.right
        elif root.right is None:
            return root.left
        min_larger_node = root.right
        while min_larger_node.left:
            min_larger_node = min_larger_node.left
        root.value = min_larger_node.value
        root.right = delete_bst(root.right, min_larger_node.value)
    return root
```

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Analysis
- **Insertion, Deletion, Search:**
  - Best/Average: $O(\log n)$ (balanced tree)
  - Worst: $O(n)$ (unbalanced tree)
- **Traversal:** $O(n)$

#### Proof & Cases
- In a balanced BST, each operation halves the search space (like binary search).
- In an unbalanced BST (like a linked list), operations can take $O(n)$ time.

## Important Notes
- BSTs are efficient for searching, inserting, and deleting if kept balanced.
- Duplicates are usually not allowed in BSTs.
- Self-balancing BSTs (like AVL or Red-Black trees) keep operations fast.

## Real-World Applications
- Databases and file systems (indexing)
- Implementing sets and maps
- Auto-complete and dictionary lookup

## Ideas for Self-Practicing
- Write a function to search for a value in a BST.
- Modify the code to count the number of nodes in a BST.
- Try implementing a self-balancing BST (see AVL trees in Chapter 4).

## Further Readings & Connections
- [Khan Academy: Binary Search Trees](https://www.khanacademy.org/computing/computer-science/algorithms/trees/a/binary-search-trees)
- [GeeksforGeeks: Binary Search Tree](https://www.geeksforgeeks.org/binary-search-tree-data-structure/)
- Learn about AVL trees (see Chapter 4) and heaps (see Chapter 11).

---
**Key Terms:**
- **Binary Search Tree (BST):** A tree where left < root < right.
- **Balanced Tree:** Tree height is minimized for fast operations.
- **Traversal:** Visiting all nodes in a tree in a specific order. 