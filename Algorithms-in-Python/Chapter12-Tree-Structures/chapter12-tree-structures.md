# Chapter 12: Tree Structures

## Introduction
This chapter introduces tree data structures, focusing on binary trees. You will learn what a tree is, how to build and traverse binary trees, and why trees are important in computer science.

## Algorithms Implemented

### 1. Simple Binary Tree Node Class
Defines a node with a value and references to left and right children.
```python
class TreeNode:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None
```

### 2. Building a Simple Binary Tree
Example of constructing a tree:
```python
root = TreeNode(1)
root.left = TreeNode(2)
root.right = TreeNode(3)
root.left.left = TreeNode(4)
```

### 3. Tree Traversal Algorithms
Traversal means visiting all nodes in a specific order.
- **Preorder (Root → Left → Right):**
  ```python
  def preorder_traversal(node):
      if node:
          print(node.value, end=" ")
          preorder_traversal(node.left)
          preorder_traversal(node.right)
  ```
- **Inorder (Left → Root → Right):**
  ```python
  def inorder_traversal(node):
      if node:
          inorder_traversal(node.left)
          print(node.value, end=" ")
          inorder_traversal(node.right)
  ```
- **Postorder (Left → Right → Root):**
  ```python
  def postorder_traversal(node):
      if node:
          postorder_traversal(node.left)
          postorder_traversal(node.right)
          print(node.value, end=" ")
  ```

### 4. Counting Nodes in a Tree
Counts the total number of nodes in a tree.
```python
def count_nodes(node):
    if not node:
        return 0
    return 1 + count_nodes(node.left) + count_nodes(node.right)
```

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Analysis
- **Traversal and counting:** $O(n)$ time, $O(h)$ space ($n$ = nodes, $h$ = tree height)

#### Proof & Cases
- Every node is visited once during traversal or counting.
- Space is used by the recursion stack (up to the height of the tree).

## Important Notes
- Trees are used to represent hierarchical data (like folders or organization charts).
- Binary trees are the basis for more advanced structures (BSTs, heaps, etc.).
- Traversal order matters for different applications.

## Real-World Applications
- File systems (folders and files)
- Expression parsing (calculators, compilers)
- Hierarchical databases

## Ideas for Self-Practicing
- Write a function to find the height of a tree.
- Modify traversal functions to collect values in a list.
- Try building a tree from a list of values.

## Further Readings & Connections
- [Khan Academy: Trees](https://www.khanacademy.org/computing/computer-science/algorithms/trees/a/introduction-to-trees)
- [GeeksforGeeks: Binary Tree](https://www.geeksforgeeks.org/binary-tree-data-structure/)
- Learn about binary search trees (see Chapter 13) and heaps (see Chapter 11).

---
**Key Terms:**
- **Tree:** A hierarchical data structure with nodes and children.
- **Binary Tree:** Each node has at most two children.
- **Traversal:** Visiting all nodes in a tree in a specific order. 