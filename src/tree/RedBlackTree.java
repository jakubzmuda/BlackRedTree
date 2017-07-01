package tree;

import static tree.Node.nil;

public class RedBlackTree {

    private Node root = nil;

    @Override
    public String toString() {
        if (root != nil) {
            return "\n*Root*\n" + root.toString();
        } else {
            return "Tree is empty";
        }
    }

    public void insert(int key) {
        Node node = new Node(key);
        Node temp = root;
        if (root == nil) {
            root = node;
            node.color = Color.BLACK;
        } else {
            node.color = Color.RED;
            while (true) {
                if (node.key < temp.key) {
                    if (temp.left == nil) {
                        temp.left = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else if (node.key >= temp.key) {
                    if (temp.right == nil) {
                        temp.right = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
            }
            balanceInsert(node);
        }
    }

    private void balanceInsert(Node node) {
        while (node.parent.color == Color.RED) {
            Node uncle;
            if (node.parent == node.parent.parent.left) {
                uncle = node.parent.parent.right;

                if (uncle != nil && uncle.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.right) {
                    node = node.parent;
                    rotateLeft(node);
                }
                node.parent.color = Color.BLACK;
                node.parent.parent.color = Color.RED;
                rotateRight(node.parent.parent);
            } else {
                uncle = node.parent.parent.left;
                if (uncle != nil && uncle.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.left) {
                    node = node.parent;
                    rotateRight(node);
                }
                node.parent.color = Color.BLACK;
                node.parent.parent.color = Color.RED;
                rotateLeft(node.parent.parent);
            }
        }
        root.color = Color.BLACK;
    }

    private void rotateLeft(Node node) {
        if (node.parent != nil) {
            if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if (node.right.left != nil) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        } else {
            Node right = root.right;
            root.right = right.left;
            right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = nil;
            root = right;
        }
    }

    private void rotateRight(Node node) {
        if (node.parent != nil) {
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }
            node.left.parent = node.parent;
            node.parent = node.left;
            if (node.left.right != nil) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        } else {
            Node left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = nil;
            root = left;
        }
    }

    private Node findNode(Node keyNode, Node node) {
        if (root == nil) {
            return null;
        }

        if (keyNode.key < node.key) {
            if (node.left != nil) {
                return findNode(keyNode, node.left);
            }
        } else if (keyNode.key > node.key) {
            if (node.right != nil) {
                return findNode(keyNode, node.right);
            }
        } else if (keyNode.key == node.key) {
            return node;
        }
        return null;
    }

    public void delete(int key) {
        Node node = new Node(key);
        if ((node = findNode(node, root)) == null) return;
        Node x;
        Node y = node;
        Color originalNodeColor = y.color;

        if (node.left == nil) {
            x = node.right;
            transplant(node, node.right);
        } else if (node.right == nil) {
            x = node.left;
            transplant(node, node.left);
        } else {
            y = minValue(node.right);
            originalNodeColor = y.color;
            x = y.right;
            if (y.parent == node)
                x.parent = y;
            else {
                transplant(y, y.right);
                y.right = node.right;
                y.right.parent = y;
            }
            transplant(node, y);
            y.left = node.left;
            y.left.parent = y;
            y.color = node.color;
        }
        if (originalNodeColor == Color.BLACK) {
            balanceDelete(x);
        }
    }

    private void transplant(Node target, Node subject) {
        if (target.parent == nil) {
            root = subject;
        } else if (target == target.parent.left) {
            target.parent.left = subject;
        } else
            target.parent.right = subject;
        subject.parent = target.parent;
    }

    private void balanceDelete(Node x) {
        while (x != root && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                    continue;
                } else if (w.right.color == Color.BLACK) {
                    w.left.color = Color.BLACK;
                    w.color = Color.RED;
                    rotateRight(w);
                    w = x.parent.right;
                }
                if (w.right.color == Color.RED) {
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                    continue;
                } else if (w.left.color == Color.BLACK) {
                    w.right.color = Color.BLACK;
                    w.color = Color.RED;
                    rotateLeft(w);
                    w = x.parent.left;
                }
                if (w.left.color == Color.RED) {
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = Color.BLACK;
    }

    private Node minValue(Node root) {
        while (root.left != nil) {
            root = root.left;
        }
        return root;
    }

}