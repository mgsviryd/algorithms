package com.sviryd.algorithms.lafore.exercise.tree.treeBinary;


import com.sviryd.algorithms.lafore.exercise.tree.AbstractTree;

public class TreeBinary<K extends Comparable<? super K>, V> extends AbstractTree {
    private static final int ORDER = 2;
    private static final int ITEMS = ORDER-1;
    private Node<K, V> root;

    @Override
    public int getItems() {
        return ITEMS;
    }
    @Override
    public int getOrder(){
        return ORDER;
    }

    public V get(K key) {
        return findNode(key).value;
    }

    private Node<K, V> findNode(K key) {
        Node<K, V> current = root;
        while (current != null) {
            int comparison = key.compareTo(current.key);
            if (comparison < 0) {
                current = current.left;
            } else if (comparison > 0) {
                current = current.right;
            } else if (comparison == 0) {
                return current;
            }
        }
        return null;
    }

    public void put(K key, V value) {
        Node<K, V> newNode = new Node(key, value);
        if (root == null) {
            root = newNode;
        } else {
            Node<K, V> current = root;
            Node<K, V> parent;
            while (true) {
                parent = current;
                if (key.compareTo(current.key) < 0) {
                    current = current.left;
                    if (current == null) {
                        newNode.parent = parent;
                        parent.left = newNode;
                        return;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        newNode.parent = parent;
                        parent.right = newNode;
                        return;
                    }
                }
            }
        }
    }

    public V delete(K key) {
        Node<K, V> cur = findNode(key);
        if (cur == null) {
            return null;
        }
        Node<K, V> par = cur.parent;
        boolean isLeft = par.left == cur;

        if (cur.left == null && cur.right == null) {
            if (cur == root) {
                root = null;
            } else if (isLeft) {
                par.left = null;
            } else {
                par.right = null;
            }
        } else if (cur.right == null) {
            if (cur == root) {
                root = cur.left;
            } else if (isLeft) {
                par.left = cur.left;
            } else {
                par.right = cur.left;
            }
        } else if (cur.left == null) {
            if (cur == root) {
                root = cur.right;
            } else if (isLeft) {
                par.left = cur.right;
            } else {
                par.right = cur.right;
            }
        } else {
            Node successor = getSuccessor(cur);
            if (cur == root) {
                root = successor;
            } else if (isLeft) {
                par.left = successor;
            } else {
                par.right = successor;
            }
            successor.left = cur.left;
        }
        return cur.value;
    }

    private Node getSuccessor(Node delete) {
        Node parentSuccessor = delete;
        Node successor = delete;
        Node current = delete.right;
        while (current != null) {
            parentSuccessor = successor;
            successor = current;
            current = current.left;
        }
        if (successor != delete.right) {
            parentSuccessor.left = successor.right;
            successor.right = delete.right;
        }
        return successor;
    }

    public void displayTraversal(TraversalType type) {
        switch (type) {
            case PREFIX:
                System.out.println("Prefix order traversal: ");
                displayPrefixOrder(root);
                break;
            case INFIX:
                System.out.println("Infix order traversal: ");
                displayInfixOrder(root);
                break;
            case POSTFIX:
                System.out.println("Postfix order traversal: ");
                displayPostfixOrder(root);
                break;
        }
    }

    private void displayPrefixOrder(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            displayPrefixOrder(node.left);
            displayPrefixOrder(node.right);
        }
    }

    private void displayInfixOrder(Node node) {
        if (node != null) {
            displayInfixOrder(node.left);
            System.out.print(node.key + " ");
            displayInfixOrder(node.right);
        }
    }

    private void displayPostfixOrder(Node node) {
        if (node != null) {
            displayPostfixOrder(node.left);
            displayPostfixOrder(node.right);
            System.out.print(node.key + " ");
        }
    }

    public V getMin() {
        Node<K, V> last = null;
        Node<K, V> cur = root;
        while (cur != null) {
            last = cur;
            cur = cur.left;
        }
        if (last != null) {
            return last.value;
        } else {
            return null;
        }
    }

    public V getMax() {
        Node<K, V> last = null;
        Node<K, V> cur = root;
        while (cur != null) {
            last = cur;
            cur = cur.right;
        }
        if (last != null) {
            return last.value;
        } else {
            return null;
        }
    }

    public int getIndexOfLevel(Node node, int level) {
        if (node.left == null && node.right == null) {
            return level;
        }
        int left = getIndexOfLevel(node.left, level + 1);
        int right = getIndexOfLevel(node.right, level + 1);
        if (left < right) {
            return right;
        } else {
            return left;
        }
    }

    private int getCapacity() {
        int level = getIndexOfLevel(root);
        int capacity = getCapacity(level,ORDER,ITEMS);
        return capacity;
    }

    private int getIndexOfLevel(Node<K, V> node) {
        return getIndexOfLevelRecursion(node, 0);
    }

    private int getIndexOfLevelRecursion(Node<K, V> node, int level) {
        int left = level;
        int right = level;
        if (node.left != null) {
            left = getIndexOfLevelRecursion(node.left, level + 1);
        }
        if (node.right != null) {
            right = getIndexOfLevelRecursion(node.right, level + 1);
        }
        if (left < right) {
            return right;
        } else {
            return left;
        }
    }

    @Override
    public Object[] getNodes() {
        int count = getCapacity();
        Object[] array = new Object[count];
        fillArrayUsingPrefixTraversal(root, array, 0);
        return array;
    }

    private void fillArrayUsingPrefixTraversal(Node<K, V> node, Object[] array, int index) {
        if (node == null) {
            return;
        }
        fillArrayUsingPrefixTraversal(node.left, array, index * 2 + 1);
        array[index] = node.value;
        fillArrayUsingPrefixTraversal(node.right, array, index * 2 + 2);
    }

    private boolean isEmpty() {
        return root == null;
    }

    private class Node<K, V> {
        private K key;
        private V value;
        private Node parent, left, right;
        private int index;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public void displayNode() {
            System.out.print('{' + key.toString() + ", " + "} ");
        }
    }
}
