## Chapter 12: Tree Structures
## This tutorial introduces tree data structures for beginners with simple, well-commented examples.

## 1. What is a Tree?
# A tree is a hierarchical data structure with a root node and zero or more child nodes.
# Each child node can itself be the root of a subtree.

## 2. Example: Simple Binary Tree Node Class
class TreeNode:
    def __init__(self, value):
        self.value = value  # The value stored in the node
        self.left = None    # Reference to the left child
        self.right = None   # Reference to the right child

## 3. Building a Simple Binary Tree
## Let's build this tree:
#      1
#     / \
#    2   3
#   / 
#  4
root = TreeNode(1)
root.left = TreeNode(2)
root.right = TreeNode(3)
root.left.left = TreeNode(4)

## 4. Tree Traversal Algorithms
## Traversal means visiting all nodes in a specific order.

## a) Preorder Traversal: Root -> Left -> Right
def preorder_traversal(node):
    if node:
        print(node.value, end=" ")  # Visit root
        preorder_traversal(node.left)
        preorder_traversal(node.right)

## b) Inorder Traversal: Left -> Root -> Right
def inorder_traversal(node):
    if node:
        inorder_traversal(node.left)
        print(node.value, end=" ")  # Visit root
        inorder_traversal(node.right)

## c) Postorder Traversal: Left -> Right -> Root
def postorder_traversal(node):
    if node:
        postorder_traversal(node.left)
        postorder_traversal(node.right)
        print(node.value, end=" ")  # Visit root

print("Preorder traversal:")
preorder_traversal(root)
print("\nInorder traversal:")
inorder_traversal(root)
print("\nPostorder traversal:")
postorder_traversal(root)
print()

## 5. Application Example: Counting Nodes in a Tree
def count_nodes(node):
    if not node:
        return 0
    return 1 + count_nodes(node.left) + count_nodes(node.right)

print("\nTotal number of nodes in the tree:", count_nodes(root))

## 6. Key Points
## - Trees are used in file systems, databases, and many algorithms.
## - Traversal is fundamental for searching, printing, or processing tree data.
## - Binary trees are a common type, but there are many variations (e.g., AVL, B-trees).

## End of Chapter 12: Tree Structures tutorial
