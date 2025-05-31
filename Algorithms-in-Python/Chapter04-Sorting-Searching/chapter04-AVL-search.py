# Python AVL Tree Tutorial for Beginners
# Reference: https://www.w3schools.com/python/python_dsa_avltrees.asp

# An AVL tree is a self-balancing binary search tree.
# Each node keeps track of its height, and the tree balances itself after insertions and deletions.

# Node class for AVL Tree
class AVLNode:
    def __init__(self, key):
        self.key = key
        self.left = None
        self.right = None
        self.height = 1  # New node is initially added at leaf

# AVL Tree class with insert and search
class AVLTree:
    # Insert a node
    def insert(self, root, key):
        # 1. Perform normal BST insert
        if not root:
            return AVLNode(key)
        elif key < root.key:
            root.left = self.insert(root.left, key)
        else:
            root.right = self.insert(root.right, key)

        # 2. Update height of this ancestor node
        root.height = 1 + max(self.get_height(root.left), self.get_height(root.right))

        # 3. Get the balance factor
        balance = self.get_balance(root)

        # 4. Balance the tree if needed
        # Left Left Case
        if balance > 1 and key < root.left.key:
            return self.right_rotate(root)
        # Right Right Case
        if balance < -1 and key > root.right.key:
            return self.left_rotate(root)
        # Left Right Case
        if balance > 1 and key > root.left.key:
            root.left = self.left_rotate(root.left)
            return self.right_rotate(root)
        # Right Left Case
        if balance < -1 and key < root.right.key:
            root.right = self.right_rotate(root.right)
            return self.left_rotate(root)

        return root

    # Search for a value in the AVL tree
    def search(self, root, key):
        if not root or root.key == key:
            return root
        if key < root.key:
            return self.search(root.left, key)
        return self.search(root.right, key)

    # Get the height of the node
    def get_height(self, node):
        if not node:
            return 0
        return node.height

    # Get balance factor
    def get_balance(self, node):
        if not node:
            return 0
        return self.get_height(node.left) - self.get_height(node.right)

    # Right rotate
    def right_rotate(self, z):
        y = z.left
        T3 = y.right
        y.right = z
        z.left = T3
        z.height = 1 + max(self.get_height(z.left), self.get_height(z.right))
        y.height = 1 + max(self.get_height(y.left), self.get_height(y.right))
        return y

    # Left rotate
    def left_rotate(self, z):
        y = z.right
        T2 = y.left
        y.left = z
        z.right = T2
        z.height = 1 + max(self.get_height(z.left), self.get_height(z.right))
        y.height = 1 + max(self.get_height(y.left), self.get_height(y.right))
        return y

    # In-order traversal (for printing)
    def in_order(self, root):
        if not root:
            return
        self.in_order(root.left)
        print(root.key, end=' ')
        self.in_order(root.right)

    # Delete a node
    def delete(self, root, key):
        # 1. Perform standard BST delete
        if not root:
            return root
        elif key < root.key:
            root.left = self.delete(root.left, key)
        elif key > root.key:
            root.right = self.delete(root.right, key)
        else:
            # Node with only one child or no child
            if not root.left:
                return root.right
            elif not root.right:
                return root.left
            # Node with two children: get the inorder successor
            temp = self.get_min_value_node(root.right)
            root.key = temp.key
            root.right = self.delete(root.right, temp.key)

        # 2. Update height
        root.height = 1 + max(self.get_height(root.left), self.get_height(root.right))

        # 3. Get balance
        balance = self.get_balance(root)

        # 4. Balance the tree
        # Left Left
        if balance > 1 and self.get_balance(root.left) >= 0:
            return self.right_rotate(root)
        # Left Right
        if balance > 1 and self.get_balance(root.left) < 0:
            root.left = self.left_rotate(root.left)
            return self.right_rotate(root)
        # Right Right
        if balance < -1 and self.get_balance(root.right) <= 0:
            return self.left_rotate(root)
        # Right Left
        if balance < -1 and self.get_balance(root.right) > 0:
            root.right = self.right_rotate(root.right)
            return self.left_rotate(root)

        return root

    def get_min_value_node(self, node):
        current = node
        while current.left:
            current = current.left
        return current

# --- Example Usage ---
avl_tree = AVLTree()
root = None

# Insert nodes
for value in [10, 20, 30, 40, 50, 25]:
    root = avl_tree.insert(root, value)

print("In-order traversal of AVL tree:")
avl_tree.in_order(root)
print()

# Search for a value
search_key = 25
found_node = avl_tree.search(root, search_key)
if found_node:
    print(f"Value {search_key} found in AVL tree.")
else:
    print(f"Value {search_key} not found in AVL tree.")

# Delete a node
root = avl_tree.delete(root, 20)
print("In-order traversal after deleting 20:")
avl_tree.in_order(root)
print()

# Summary:
# - AVL trees keep themselves balanced for fast search, insert, and delete (O(log n) time)
# - Rotations are used to maintain balance after changes
