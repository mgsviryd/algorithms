package com.sviryd.algorithms.lafore.exercises.heap;

import java.util.Comparator;
import java.util.Stack;

public class HeapLink<V extends Comparable<? super V>> implements IHeap<V>{
    private Link<V> root;
    private Link<V> parentOfLast;
    private int count;
    private Comparator<V> comparator;
    private int negative, zero, positive;

    public HeapLink() {
        setDescendingOrder();
    }

    public HeapLink(boolean isAscendingOrder) {
        if (isAscendingOrder) {
            setAscendingOrder();
        } else {
            setDescendingOrder();
        }
    }

    public HeapLink(Comparator<V> comparator) {
        this.comparator = comparator;
    }

    private void setDescendingOrder() {
        negative = -1;
        zero = 0;
        positive = 1;
    }

    private void setAscendingOrder() {
        negative = 1;
        zero = 0;
        positive = -1;
    }

    private int negativeZeroPositive(int n) {
        return (n < 0) ? -1 : ((n == 0) ? 0 : 1);
    }
    @Override
    public boolean isEmpty() {
        return root == null;
    }


    private Link<V> getLink(int index) {
        if (index>=count || index<0){
            return null;
        }
        if (index==0){
            return root;
        }
        Stack<Integer> way = getBinaryWayToRoot(index);
        Link<V> findLink = getLinkUsingBinaryWayToRoot(way);
        return findLink;

    }

    private Stack<Integer> getBinaryWayToRoot(int index) {
        Stack<Integer> way = new Stack<>();
        int leftBeforeRoot = 1;
        int rightBeforeRoot = 2;
        int remainderForLeft = 1;
        int remainderForRight = 0;
        while (true){
            if(index==leftBeforeRoot){
                way.push(remainderForLeft);
                break;
            }
            if (index==rightBeforeRoot){
                way.push(remainderForRight);
                break;
            }
            int remainder = index%2;
            way.push(remainder);
            if (index<=1){ // base restriction
                break;
            }
            index=(index-1)/2; // find index of parent
        }
        return way;
    }

    private Link<V> getLinkUsingBinaryWayToRoot(Stack<Integer> way) {
        Link<V> findLink = root;
        while (!way.isEmpty()){
            int direction =  way.pop();
            int remainderForLeft = 1;
            if (direction== remainderForLeft){
                findLink=findLink.left;
            }
            else {
                findLink=findLink.right;
            }
        }
        return findLink;
    }

    private Link<V> getParentLink(int indexLast) {
        return getLink(indexLast / 2);
    }
    @Override
    public void insert(V value) {
        Link<V> newLink = new Link(value);
        if (root == null) {
            newLink.index=0;
            root = newLink;
            parentOfLast = newLink;
        } else {
            if (parentOfLast.left == null) {
                parentOfLast.left = newLink;
                newLink.index = parentOfLast.index * 2 + 1;
            } else {
                parentOfLast.right = newLink;
                newLink.index = parentOfLast.index * 2 + 2;
            }
            newLink.parent = parentOfLast;
            parentOfLast = getParentLink(newLink.index);
        }
        count++;
        trickleUp(newLink);
    }
    @Override
    public V remove() {
        if (isEmpty()){
            return null;
        }
        V rootValue = root.value;
        Link<V> bottomLink;
        if (parentOfLast == null) {
            parentOfLast = null;
            root = null;
            return rootValue;
        }
        if (parentOfLast.right!=null) {
            bottomLink = parentOfLast.right;
            parentOfLast.right = null;
        } else {
            bottomLink = parentOfLast.left;
            parentOfLast.left = null;
            parentOfLast = getLink(parentOfLast.index-1);
        }
        if (parentOfLast!=null && parentOfLast!=root){
            bottomLink.left = root.left;
            bottomLink.right = root.right;
        }
        else if(parentOfLast==root){
            bottomLink.left = root.left;
        }
        bottomLink.index = 0;

        root = bottomLink;
        --count;
        trickleDown(root);
        return rootValue;
    }

    private void trickleUp(Link<V> bottom) {
        Link <V> ceiling = bottom.parent;
        V value = bottom.value;
        int comparison;
        while (ceiling!=null) {
            if (comparator != null) {
                comparison = negativeZeroPositive(comparator.compare(ceiling.value, value));
            } else {
                comparison = negativeZeroPositive(ceiling.value.compareTo(value));
            }
            if (comparison == zero || comparison == positive) {
                break;
            }
            bottom.value = ceiling.value;
            bottom = ceiling;
            ceiling = ceiling.parent;
        }
            bottom.value = value;
    }

    private void trickleDown(Link <V> top) {
        V topValue = top.value;
        Link<V> largeChild = top;
        while (top.index < count / 2) {
            Link<V> leftChild = top.left;
            Link<V> rightChild = top.right;
            int comparison;
            if (comparator != null) {
                if (rightChild == null){
                    largeChild = leftChild;
                }
                else {
                    comparison = negativeZeroPositive(comparator.compare(leftChild.value, rightChild.value));
                    if (rightChild.index < count && comparison == negative) {
                        largeChild = rightChild;
                    } else {
                        largeChild = leftChild;
                    }
                }
                comparison = negativeZeroPositive(comparator.compare(topValue, largeChild.value));
                if (comparison == positive) {
                    break;
                }
            } else {
                if (rightChild == null){
                    largeChild = leftChild;
                }
                else {
                    comparison = negativeZeroPositive(leftChild.value.compareTo(rightChild.value));
                    if (rightChild.index < count && comparison == negative) {
                        largeChild = rightChild;
                    } else {
                        largeChild = leftChild;
                    }
                    comparison = negativeZeroPositive(topValue.compareTo(largeChild.value));
                    if (comparison == positive) {
                        break;
                    }
                }
            }
            top.value = largeChild.value;
            top = largeChild;
        }
        largeChild.value = topValue;
    }

    @Override
    public boolean change(int index, V newValue) {
        if (index < 0 || index > count - 1) {
            return false;
        }
        Link<V> link = getLink(index);
        V oldValue = link.value;
        link.value = newValue;
        int comparison = negativeZeroPositive(oldValue.compareTo(newValue));
        if (comparison == negative) {
            trickleUp(link);
        } else {
            trickleDown(link);
        }
        return true;
    }
    @Override
    public void sort(Comparator<V> comparator) {
        Link main = root;
        root = null;
        this.comparator = comparator;
        count = 0;
        insertAll(main);
    }


    private void insertAll(Link<V> link) {
        if (link == null) {
            return;
        }
        insertAll(link.left);
        insertAll(link.right);
        insert(link.value);
    }
    @Override
    public int getIndex(Comparator<V> comparator, V value) {
        Link<V> current = root;
        Link<V> properLink = getProperLink(comparator, value, current);
        if (properLink != null){
            return properLink.index;
        }
        else {
            return -1;
        }
    }

    private Link<V> getProperLink(Comparator<V> comparator, V value, Link<V> current) {
        if (current!=null && comparator.compare(value,current.value) == 0){
            return current;
        }
            if (current.left!=null){
                Link<V> left = getProperLink(comparator,value,current.left);
                if (left!=null){
                    return left;
                }
            }
            if (current.right!=null){
                Link<V> right = getProperLink(comparator,value,current.right);
                if (right!=null){
                    return right;
                }
            }
        return null;
    }

    @Override
    public V getValue(int index) {
        return getLink(index).value;
    }

    @Override
    public int getSize() {
        return count;
    }

    @Override
    public void displayHeap() {
        // format of array
        System.out.print("heapArray: ");
        for (int m = 0; m < count; m++) {
            Link findLink = getLink(m);
            if (findLink != null) {
                System.out.print(findLink.value + " ");
            } else {
                System.out.print("-- ");
            }
        }
        System.out.println();
        // format of heap
        int nBlanks = 32;
        int itemsPerRow = 1;
        int column = 0;
        int j = 0;
        String dots = "...............................";
        System.out.println(dots + dots);
        while (count > 0) {
            if (column == 0) {
                for (int k = 0; k < nBlanks; k++) {
                    System.out.print(' ');
                }
            }
            if (getLink(j)==null){
                System.out.print(" ");
            }
            else {
                System.out.print(getLink(j).value);
            }
            if (++j == count) {
                break;
            }
            if (++column == itemsPerRow) {
                nBlanks /= 2;
                itemsPerRow *= 2;
                column = 0;
                System.out.println();
            } else {
                for (int k = 0; k < nBlanks * 2 - 2; k++) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println("\n" + dots + dots);
    }

    private static class Link<V extends Comparable<? super V>> {
        private V value;
        private Link parent, left, right;
        private int index;

        private Link(V value) {
            this.value = value;
        }
    }
}
