
Objectives

- To design and implement a binary search tree (§25.2).
- To represent binary trees using linked data structures (§25.2.1).
- To search an element in binary search tree (§25.2.2).
- To insert an element into a binary search tree (§25.2.3).
- To traverse elements in a binary tree (§25.2.4).
- To delete elements from a binary search tree (§25.3).
- To display binary tree graphically (§25.4).
- To create iterators for traversing a binary tree (§25.5).
- To implement Huffman coding for compressing data using a binary tree (§25.6).

Lecture 11, "**Binary Search Trees**," introduces a fundamental **hierarchical data structure** known as a binary search tree (BST). Unlike linear structures such as lists, stacks, or queues, a binary tree is either empty or comprises a root element and two distinct binary trees, known as the left and right subtrees.

The primary **objectives** of this lecture are to:
*   Design and implement a binary search tree.
*   Represent binary trees using linked data structures.
*   Explain how to search for, insert, and delete elements in a binary search tree.
*   Describe various methods for traversing elements within a binary tree.
*   Graphically display binary trees.
*   Create iterators for tree traversal.
*   Implement Huffman coding for data compression using binary trees.

### What is a Binary Search Tree (BST)?
A special type of binary tree, known as a **binary search tree**, is highly useful for efficient data management. A BST with no duplicate elements possesses a crucial property: for every node in the tree, **the value of any node in its left subtree is less than the value of the node, and the value of any node in its right subtree is greater than the value of the node**.

### Representing Binary Trees
Binary trees are commonly represented using a **set of linked nodes**. Each node, often defined as a `TreeNode` class, contains an `element` (value) and two links: `left` and `right`, which reference its left and right child nodes, respectively. If a child doesn't exist, its link would be `null`.

### Core Operations and Algorithms

1.  **Searching an Element**:
    *   To search for an element, the algorithm starts from the `root` of the tree.
    *   It iteratively compares the `element` being searched with the `current` node's element.
    *   If the search `element` is less than the `current` node's element, it moves to the `left` child.
    *   If the search `element` is greater, it moves to the `right` child.
    *   If a match is found, `true` is returned; if `current` becomes `null` (meaning no match found), `false` is returned.

2.  **Inserting an Element**:
    *   If the binary tree is empty, the new element becomes the `root` node.
    *   Otherwise, the algorithm locates the `parent` node where the new element should be attached. This involves traversing the tree similar to searching, comparing the new element's value to current node values until a `null` child link is encountered.
    *   If the new element's value is less than the `parent`'s element, it becomes the `parent`'s `left` child.
    *   If it's greater, it becomes the `parent`'s `right` child.
    *   Duplicate nodes are typically not inserted.

3.  **Deleting Elements**:
    To delete an element, both the node containing the element (`current`) and its `parent` node must be located. There are two main cases:
    *   **Case 1: The current node does not have a left child**. In this scenario, the parent node is simply connected to the `current` node's `right` child. If the `current` node has no children, its link from the parent is set to `null`.
    *   **Case 2: The current node has a left child**. This is more complex. The algorithm finds the node with the largest element in the `current` node's left subtree (this node is often called `rightMost`). The element from `rightMost` replaces the element in the `current` node. Then, the `rightMost` node itself is deleted by connecting its parent (or the `current` node, if `rightMost` was its immediate left child) to `rightMost`'s `left` child.

### Tree Traversal
Tree traversal is the process of visiting each node in the tree exactly once. The lecture describes several ways to traverse a tree:
*   **Inorder Traversal**: Visits the left subtree, then the current node, then the right subtree. This results in elements being visited in **sorted order** in a BST.
*   **Postorder Traversal**: Visits the left subtree, then the right subtree, and finally the current node.
*   **Preorder Traversal**: Visits the current node, then the left subtree, and finally the right subtree.
*   **Breadth-First Traversal**: Visits nodes level by level, starting from the root, then its children from left to right, then grandchildren from left to right, and so on.

### Time Complexity
The time complexity for operations like searching, insertion, and deletion in a binary search tree is directly dependent on the **height of the tree**.
*   In the **worst case**, if the tree is unbalanced (e.g., resembles a linked list), the height can be **O(n)**, leading to O(n) complexity for these operations.
*   For traversals (inorder, preorder, postorder), the time complexity is **O(n)**, as each node is visited exactly once.

### Other Concepts
*   **Iterators**: Provide a uniform way to traverse elements within a container like a binary tree.
*   **Huffman Coding**: Presented as a case study that utilizes binary trees for data compression. It works by encoding frequently occurring characters with fewer bits, constructing a Huffman coding tree based on character frequencies. This is achieved using a greedy algorithm that repeatedly combines two trees with the smallest weights (frequencies).

The lecture outlines the `Tree` interface, which defines common operations for trees, and the `BST` class, a concrete implementation that extends `AbstractTree`.

Sample code 


- https://liveexample.pearsoncmg.com/dsanimation/BSTeBook.html
- https://liveexample.pearsoncmg.com/html/Tree.html
- https://liveexample.pearsoncmg.com/html/BST.html
- https://liveexample.pearsoncmg.com/html/TestBST.html
- https://liveexample.pearsoncmg.com/html/TestBSTDelete.html
- https://liveexample.pearsoncmg.com/html/BTView.html
- https://liveexample.pearsoncmg.com/html/BSTAnimation.html
- https://liveexample.pearsoncmg.com/html/TestBSTWithIterator.html
- https://liveexample.pearsoncmg.com/html/HuffmanCode.html