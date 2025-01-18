package com.sviryd.algorithms.lafore.exercise.tree.treeBinary;

import com.sviryd.algorithms.lafore.exercise.tree.AbstractTree;

import java.util.Stack;

/**
 * Exercise 8.1 from Robert Lafore Structures of data and algorithms
 */
public class TreeBinaryOnlyLeaf<V> extends AbstractTree {
    private static final int ORDER = 2;
    private static final int ITEMS = ORDER-1;
    private Node<V> root;
    private V vDefault;
    private int iLast;
    private int count;

    public TreeBinaryOnlyLeaf(V defaultValue) {
        this.vDefault = defaultValue;
    }

    @Override
    public int getItems() {
        return ITEMS;
    }
    @Override
    public int getOrder(){
        return ORDER;
    }

    public void put(V value) {
        Node<V> node = new Node<>(value);
        if (root == null) {
            node.index = 0;
            root = node;
            iLast = 0;
        } else {
            Node<V> lastNode = getNode(iLast);
            ++iLast;
            V vLeft = lastNode.value;
            V vRight = value;
            lastNode.value = vDefault;
            Node<V> left = new Node<>(vLeft);
            left.index = lastNode.index * 2 + 1;
            lastNode.left = left;
            Node<V> right = new Node<>(vRight);
            right.index = lastNode.index * 2 + 2;
            lastNode.right = right;
        }
        ++count;
    }

    private Node<V> getNode(int index) {
        if (index >= count || index < 0) {
            return null;
        }
        if (index == 0) {
            return root;
        }
        Stack<Integer> way = getBinaryWayToRoot(index);
        Node<V> node = getNodeUsingBinaryWayToRoot(way);
        return node;
    }

    private Stack<Integer> getBinaryWayToRoot(int index) {
        Stack<Integer> way = new Stack<>();
        int leftBeforeRoot = 1;
        int rightBeforeRoot = 2;
        int remainderForLeft = 1;
        int remainderForRight = 0;
        while (true) {
            if (index == leftBeforeRoot) {
                way.push(remainderForLeft);
                break;
            }
            if (index == rightBeforeRoot) {
                way.push(remainderForRight);
                break;
            }
            int remainder = index % 2;
            way.push(remainder);
            if (index <= 1) { // base restriction
                break;
            }
            index = (index - 1) / 2; // find index of parent
        }
        return way;
    }

    private Node<V> getNodeUsingBinaryWayToRoot(Stack<Integer> way) {
        Node<V> node = root;
        while (!way.isEmpty()) {
            int direction = way.pop();
            int remainderForLeft = 1;
            if (direction == remainderForLeft) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    @Override
    public Object[] getNodes() {
        int count = getCapacity();
        Object[] array = new Object[count];
        fillArrayUsingPrefixTraversal(root, array);
        return array;
    }

    private void fillArrayUsingPrefixTraversal(Node<V> node, Object[] array) {
        if (node == null) {
            return;
        }
        fillArrayUsingPrefixTraversal(node.left, array);
        array[node.index] = node.value;
        fillArrayUsingPrefixTraversal(node.right, array);
    }

    private int getCapacity() {
        int level = getIndexOfLevel(root);
        int capacity = getCapacity(level,ORDER,ITEMS);
        return capacity;
    }

    private int getIndexOfLevel(Node<V> node) {
        return getIndexOfLevelRecursion(node, 0);
    }

    private int getIndexOfLevelRecursion(Node<V> node, int level) {
        if (node.left == null && node.right == null) {
            return level;
        }
        int left = getIndexOfLevelRecursion(node.left, level + 1);
        int right = getIndexOfLevelRecursion(node.right, level + 1);
        if (left < right) {
            return right;
        } else {
            return left;
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    private class Node<V> {
        private V value;
        private Node<V> left, right;
        private int index;

        public Node(V value) {
            this.value = value;
        }

        public void displayNode() {
            System.out.print('{' + value.toString() + ", " + "} ");
        }
    }
}
