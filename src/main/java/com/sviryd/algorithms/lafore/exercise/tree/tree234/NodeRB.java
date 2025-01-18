package com.sviryd.algorithms.lafore.exercise.tree.tree234;

public class NodeRB<K extends Comparable<K>, V> {
    private K key;           // key
    private V val;         // associated data
    private int indexConvertToArray;
    private NodeRB left, right;  // links to left and right subtrees
    private boolean color;     // color of parent link
    private int size;          // subtree count

    public NodeRB(K key, V val, boolean color, int size, int indexConvertToArray) {
        this.key = key;
        this.val = val;
        this.color = color;
        this.size = size;
        this.indexConvertToArray=indexConvertToArray;
    }

    public NodeRB getLeft() {
        return left;
    }

    public void setLeft(NodeRB left) {
        this.left = left;
    }

    public NodeRB getRight() {
        return right;
    }

    public void setRight(NodeRB right) {
        this.right = right;
    }

    public boolean isRed(){
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getIndexConvertToArray() {
        return indexConvertToArray;
    }

    public void setIndexConvertToArray(int indexConvertToArray) {
        this.indexConvertToArray = indexConvertToArray;
    }

    @Override
    public String toString() {
        if (color){
            return key.toString();
        }
        else {
            return "{"+key+"}";
        }
    }
}
