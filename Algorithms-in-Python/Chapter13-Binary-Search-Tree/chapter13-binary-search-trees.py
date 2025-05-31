## Chapter 13: Binary Search Trees (BST)
## This tutorial introduces binary search trees for beginners with simple, well-commented examples.

## 1. What is a Binary Search Tree?
# A BST is a tree where each node has at most two children.
# For any node, all values in the left subtree are less, and all values in the right subtree are greater.

class BSTNode:
    def __init__(self, value):
        self.value = value  # Value stored in the node
        self.left = None    # Left child
        self.right = None   # Right child

## 2. Insertion in BST
def insert_bst(root, value):
    """Insert a value into the BST and return the root."""
    if root is None:
        return BSTNode(value)
    if value < root.value:
        root.left = insert_bst(root.left, value)
    elif value > root.value:
        root.right = insert_bst(root.right, value)
    # If value == root.value, do nothing (no duplicates in this example)
    return root

## 3. Inorder Traversal (prints values in sorted order)
def inorder_traversal(node):
    if node:
        inorder_traversal(node.left)
        print(node.value, end=" ")
        inorder_traversal(node.right)

## 4. Deletion in BST
def delete_bst(root, value):
    """Delete a value from the BST and return the new root."""
    if root is None:
        return None
    if value < root.value:
        root.left = delete_bst(root.left, value)
    elif value > root.value:
        root.right = delete_bst(root.right, value)
    else:
        # Node with only one child or no child
        if root.left is None:
            return root.right
        elif root.right is None:
            return root.left
        # Node with two children: get the inorder successor (smallest in the right subtree)
        min_larger_node = root.right
        while min_larger_node.left:
            min_larger_node = min_larger_node.left
        root.value = min_larger_node.value
        root.right = delete_bst(root.right, min_larger_node.value)
    return root

## 5. Example Usage
## Create a BST and perform operations
values_to_insert = [50, 30, 70, 20, 40, 60, 80]
bst_root = None
for val in values_to_insert:
    bst_root = insert_bst(bst_root, val)

print("BST Inorder Traversal (sorted order):")
inorder_traversal(bst_root)
print()

# Delete a value
bst_root = delete_bst(bst_root, 70)
print("BST after deleting 70:")
inorder_traversal(bst_root)
print()

## 6. Key Points
## - BSTs allow fast search, insertion, and deletion (average O(log n) time).
## - Inorder traversal of a BST gives sorted order.
## - Deletion handles three cases: leaf, one child, two children.

## End of Chapter 13: Binary Search Trees tutorial
