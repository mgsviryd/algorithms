package com.test.structureOfData.lafore.exercises.tree.tree234;

public class Tree234<K extends Comparable<? super K>, V>{
    private static final int ORDER = 4;
    private static final int ITEMS = ORDER - 1;
    private Node<K, V> root;

    public Tree234() {
        root = new Node<>();
    }

    public int getItems() {
        return ITEMS;
    }
    public int getOrder(){
        return ORDER;
    }

    public void put(K key, V value) {
        if (value == null){
            throw new IllegalArgumentException("Argument value in put method must be not null.");
        }
        Node<K, V> current = root;
        Item<K, V> newItem = new Item(key, value);
        while (true) {
            if (current.isFull()) {
                split(current);
                current = current.getParent();
                Node<K, V> temp = current;
                current = getNextChild(current, key);
                if (current == temp) {
                    break;
                }
            } else if (current.isLeaf()) {
                break;
            } else {
                Node<K, V> temp = current;
                current = getNextChild(current, key);
                if (current == temp) {
                    break;
                }
            }
        }
        current.insertItem(newItem);
    }

    public void split(Node<K, V> current) {
        Item<K, V> itemB, itemC;
        Node<K, V> parent, child2, child3;
        itemC = current.removeLastItem();
        itemB = current.removeLastItem();
        child2 = current.disconnectChild(2);
        child3 = current.disconnectChild(3);
        if (current == root) {
            root = new Node();
            parent = root;
            root.connectChild(0, current);
        } else {
            parent = current.getParent();
        }
        int iInsert = parent.insertItem(itemB);
        // drift a parent child if itemB isn't inserted in the end
        int count = parent.getCountOfItems();
        int iLast = count - 1;
        for (int i = iLast; i > iInsert; i--) {
            Node<K, V> temp = parent.disconnectChild(i);
            parent.connectChild(i + 1, temp);
        }

        Node<K, V> newRight = new Node();
        parent.connectChild(iInsert + 1, newRight);
        newRight.insertItem(itemC);
        newRight.connectChild(0, child2);
        newRight.connectChild(1, child3);
    }

    private Node<K, V> getNextChild(Node<K, V> previous, K key) {
        int i;
        for (i = 0; i < previous.getCountOfItems(); i++) {
            if (key.compareTo(previous.getItem(i).getKey()) == 0) {
                return previous;
            }
            if (key.compareTo(previous.getItem(i).getKey()) < 0) {
                return previous.getChild(i);
            }
        }
        return previous.getChild(i);
    }

    public V get(K key) {
        Node<K, V> current = root;
        int index;
        while (true) {
            if ((index = current.findItem(key)) != -1) {
                Item<K, V> findItem = current.getItem(index);
                V value = findItem.getValue();
                return value;
            } else if (current.isLeaf()) {
                return null;
            } else {
                current = getNextChild(current, key);
            }
        }
    }


    public K findMinKey() {
        return findMinNode().getItem(0).getKey();
    }

    private Node<K, V> findMinNode() {
        return findMinNode(root);
    }

    private Node<K, V> findMinNode(Node<K, V> node) {
        if (node.isLeaf()) {
            return node;
        } else {
            return findMinNode(node.getChild(0));
        }
    }

    private int getIndexOfLevel(Node<K, V> node) {
        if (node == null){
            throw new IllegalArgumentException("Argument in getIndexOfLevel can't be null.");
        }
        return getIndexOfLevelRecursion(node, 0);
    }

    private int getIndexOfLevelRecursion(Node<K, V> node, int level) {
        if (node.isLeaf()) {
            return level;
        }
        level = getIndexOfLevelRecursion(node.childArray[0], level + 1);
        return level;
    }

    //TODO
    public NodeRB convertInRedBlackBST() {
        Node root234 = root;
        NodeRB nodeRB = makeStructureRedBlackBST(root234, 0);
//        nodeRB = balanced(nodeRB);
        nodeRB.setColor(false);
        return nodeRB;
    }
    private NodeRB findNodeRBWithMinIndex(NodeRB nodeRB){
        if (nodeRB==null){
            return null;
        }
        NodeRB left = findNodeRBWithMinIndex(nodeRB.getLeft());
        NodeRB right = findNodeRBWithMinIndex(nodeRB.getRight());
        if (left!=null && left.getIndexConvertToArray()==0){
            return left;
        }
        if (right!=null && right.getIndexConvertToArray()==0){
            return right;
        }
        return nodeRB;
    }


    private NodeRB makeStructureRedBlackBST(Node node234, int indexToArray) {
        if (node234 == null) {
            return null;
        }
        NodeRB nodeRB = null;
        NodeRB leftNodeRB;
        NodeRB rightNodeRB;
        int sizeLeftNodeRBLeft;
        int sizeLeftNodeRBRight;
        int sizeRightNodeRBRLeft;
        int sizeRightNodeRBRight;

        int size = 1;
        int num = node234.getCountOfItems();
        switch (num) {
            case 1:
                nodeRB = new NodeRB(node234.getItem(0).getKey(), node234.getItem(0).getValue(), false, size, indexToArray);
                leftNodeRB = makeStructureRedBlackBST(node234.getChild(0), 2 * indexToArray + 1);
                rightNodeRB = makeStructureRedBlackBST(node234.getChild(1), 2 * indexToArray + 2);
                int sizeNodeRBLeft = nodeRB.getLeft() != null ? nodeRB.getLeft().getSize() : 0;
                int sizeNodeRBRight = nodeRB.getRight() != null ? nodeRB.getRight().getSize() : 0;
                nodeRB.setSize(size + sizeNodeRBLeft + sizeNodeRBRight);
                nodeRB.setLeft(leftNodeRB);
                nodeRB.setRight(rightNodeRB);
                break;
            case 2:
                nodeRB = new NodeRB(node234.getItem(1).getKey(), node234.getItem(1), false, size, indexToArray);
                leftNodeRB = new NodeRB(node234.getItem(0).getKey(), node234.getItem(0).getKey(), true, size, 2 * indexToArray + 1);
                NodeRB leftNodeRBLeft = makeStructureRedBlackBST(node234.getChild(0), 2 * leftNodeRB.getIndexConvertToArray() + 1);
                NodeRB leftNodeRBRight = makeStructureRedBlackBST(node234.getChild(1), 2 * leftNodeRB.getIndexConvertToArray() + 2);
                rightNodeRB = makeStructureRedBlackBST(node234.getChild(2), 2 * indexToArray + 2);
                sizeLeftNodeRBLeft = leftNodeRB.getLeft() != null ? leftNodeRB.getLeft().getSize() : 0;
                sizeLeftNodeRBRight = leftNodeRB.getRight() != null ? leftNodeRB.getRight().getSize() : 0;
                leftNodeRB.setSize(size + sizeLeftNodeRBLeft + sizeLeftNodeRBRight);
                sizeNodeRBRight = nodeRB.getRight() != null ? nodeRB.getRight().getSize() : 0;
                nodeRB.setSize(size + sizeLeftNodeRBLeft + sizeLeftNodeRBRight + sizeNodeRBRight);
                leftNodeRB.setLeft(leftNodeRBLeft);
                leftNodeRB.setRight(leftNodeRBRight);
                nodeRB.setLeft(leftNodeRB);
                nodeRB.setRight(rightNodeRB);
                break;
            case 3:
                nodeRB = new NodeRB(node234.getItem(1).getKey(), node234.getItem(1), false, size, indexToArray);
                leftNodeRB = new NodeRB(node234.getItem(0).getKey(), node234.getItem(0).getValue(), true, size, 2 * indexToArray + 1);
                rightNodeRB = new NodeRB(node234.getItem(2).getKey(), node234.getItem(2).getValue(), true, size, 2 * indexToArray + 2);
                leftNodeRBLeft = makeStructureRedBlackBST(node234.getChild(0), 2 * leftNodeRB.getIndexConvertToArray() + 1);
                leftNodeRBRight = makeStructureRedBlackBST(node234.getChild(1), 2 * leftNodeRB.getIndexConvertToArray() + 2);
                NodeRB rightNodeRBLeft = makeStructureRedBlackBST(node234.getChild(2), 2 * rightNodeRB.getIndexConvertToArray() + 1);
                NodeRB rightNodeRBRight = makeStructureRedBlackBST(node234.getChild(3), 2 * rightNodeRB.getIndexConvertToArray() + 2);
                sizeLeftNodeRBLeft = leftNodeRBLeft != null ? leftNodeRBLeft.getSize() : 0;
                sizeLeftNodeRBRight = leftNodeRBRight != null ? leftNodeRBRight.getSize() : 0;
                sizeRightNodeRBRLeft = rightNodeRBLeft != null ? rightNodeRBLeft.getSize() : 0;
                sizeRightNodeRBRight = rightNodeRBRight != null ? rightNodeRBRight.getSize() : 0;
                leftNodeRB.setSize(size + sizeLeftNodeRBLeft + sizeLeftNodeRBRight);
                rightNodeRB.setSize(size + sizeRightNodeRBRLeft + sizeRightNodeRBRight);
                nodeRB.setSize(size + sizeLeftNodeRBLeft + sizeLeftNodeRBRight + sizeRightNodeRBRLeft + sizeRightNodeRBRight);
                leftNodeRB.setLeft(leftNodeRBLeft);
                leftNodeRB.setRight(leftNodeRBRight);
                rightNodeRB.setLeft(rightNodeRBLeft);
                rightNodeRB.setRight(rightNodeRBRight);
                nodeRB.setLeft(leftNodeRB);
                nodeRB.setRight(rightNodeRB);
                break;
        }
        return nodeRB;
    }

    private NodeRB balanced(NodeRB nodeRB) {
        if (nodeRB == null) {
            return nodeRB;
        }
        NodeRB left = balanced(nodeRB.getLeft());
        NodeRB right = balanced(nodeRB.getRight());
        nodeRB.setLeft(left);
        nodeRB.setRight(right);

        if (left == null || right == null) {
            return nodeRB;
        }
        if (left.isRed() && right.isRed()) flipColors(nodeRB);
        if (left.isRed() && !nodeRB.isRed()) nodeRB = rotateRight(nodeRB);
        if (left.isRed() && right.isRed()) flipColors(nodeRB);
        if (right.isRed() && !nodeRB.isRed()) nodeRB = rotateLeft(nodeRB);
        nodeRB.setSize(nodeRB.getLeft().getSize() + nodeRB.getRight().getSize() + 1);
        return nodeRB;
    }

    private void flipColors(NodeRB h) {
        h.setColor(!h.isRed());
        h.getLeft().setColor(!h.getLeft().isRed());
        h.getRight().setColor(!h.getRight().isRed());
    }

    private NodeRB rotateRight(NodeRB h) {
        NodeRB x = h.getLeft();
        h.setLeft(x.getRight());
        x.setRight(h);
        x.setColor(false);
        x.getRight().setColor(true);
        x.setSize(h.getSize());
        h.setSize(h.getLeft().getSize() + h.getRight().getSize() + 1);
        repairIndex(x, h.getIndexConvertToArray());
//        if (x.getLeft().isRed() && x.getRight().isRed()) flipColors(x);
        return x;
    }

    private NodeRB rotateLeft(NodeRB h) {
        NodeRB x = h.getRight();
        h.setRight(x.getLeft());
        x.setLeft(h);
        x.setColor(false);
        x.getLeft().setColor(true);
        x.setSize(h.getSize());
        h.setSize(h.getLeft().getSize() + h.getRight().getSize() + 1);
        repairIndex(x, h.getIndexConvertToArray());
        if (x.getLeft().isRed() && x.getRight().isRed()) flipColors(x);
        return x;
    }

    private void repairIndex(NodeRB nodeRB, int index) {
        if (nodeRB == null) {
            return;
        }
        nodeRB.setIndexConvertToArray(index);
        if (nodeRB.getLeft() != null) {
            repairIndex(nodeRB.getLeft(), 2 * index + 1);
        }
        if (nodeRB.getRight() != null) {
            repairIndex(nodeRB.getRight(), 2 + index + 2);
        }

    }

    private int findCountOfLevel(NodeRB input, int level) {
        if (input == null) {
            return level;
        }
        int left = findCountOfLevel(input.getLeft(), level + 1);
        int right = findCountOfLevel(input.getRight(), level + 1);
        if (left < right) {
            return right;
        } else {
            return left;
        }

    }

    private int findAllNodeInTree(int maxLevel) {
        int root = 1;
        int allNodeWithoutRoot = 2;
        if (maxLevel == 0) {
            return root;
        }
        for (int i = 2; i <= maxLevel; i++) {
            allNodeWithoutRoot = allNodeWithoutRoot + (int) Math.pow(2, i);
        }
        return allNodeWithoutRoot + root;
    }

    private void shiftTreeInArrayThroughPreOrder(NodeRB node, NodeRB[] array) {
        if (node == null) {
            return;
        }
        shiftTreeInArrayThroughPreOrder(node.getLeft(), array);
        array[node.getIndexConvertToArray()] = node;
        shiftTreeInArrayThroughPreOrder(node.getRight(), array);
    }

    public void displayRBThroughArray(NodeRB root) {
        int level = findCountOfLevel(root, 0);
        int allNodeInTree = findAllNodeInTree(level);
        int allNodeInLevel = (int) Math.pow(2, level) * 2 + 1;
        NodeRB[] array = new NodeRB[allNodeInTree];
        shiftTreeInArrayThroughPreOrder(root, array);

        for (int i = 0; i <= level; i++) {
            int allNodeInLevelCurrent = (int) Math.pow(2, i);
            int interval = allNodeInLevel / (allNodeInLevelCurrent + 2) + 1;
            if (i == 0) {
                interval = allNodeInLevel / 2;
            }
            int firstIndexArrayOnLevel;
            if (i == 0) {
                firstIndexArrayOnLevel = 0;
            } else {
                firstIndexArrayOnLevel = findAllNodeInTree(i - 1);
            }
            int lastIndexArrayOnLevel = firstIndexArrayOnLevel + allNodeInLevelCurrent - 1;
            int lowerMiddleIndex = (firstIndexArrayOnLevel + lastIndexArrayOnLevel) / 2;
            int middleUpperIndex = lowerMiddleIndex + 1;
            NodeRB[] arrayPrint = new NodeRB[allNodeInLevel];
            int middleIndex = allNodeInLevel / 2;
            int middleIndexDown = middleIndex;
            int middleIndexUp = middleIndex;
            if (i == 0) {
                arrayPrint[middleIndex] = array[0];
            } else {
                while (allNodeInLevelCurrent != 0) {
                    arrayPrint[middleIndexDown -= interval] = array[lowerMiddleIndex--];
                    --allNodeInLevelCurrent;
                    arrayPrint[middleIndexUp += interval] = array[middleUpperIndex++];
                    --allNodeInLevelCurrent;
                }
            }
            char split = ' ';
            for (int j = 0; j < allNodeInLevel; j++) {
                if (arrayPrint[j] == null) {
                    System.out.print(split);
                } else {
                    System.out.print(arrayPrint[j]);
                }
            }
            System.out.println();
        }
    }

    private static class Node<K extends Comparable<? super K>, V> {
        private int nItems;
        private Node<K, V> parent;
        private Node<K, V> childArray[];
        private Item<K, V> itemArray[];

        public Node() {
            childArray = new Node[ORDER];
            itemArray = new Item[ITEMS];
        }

        public void connectChild(int index, Node<K, V> child) {
            childArray[index] = child;
            if (child != null) {
                child.parent = this;
            }
        }

        public Node<K, V> disconnectChild(int index) {
            Node<K, V> temp = childArray[index];
            childArray[index] = null;
            return temp;
        }

        public Node<K, V> getChild(int index) {
            return childArray[index];
        }

        public Node<K, V> getParent() {
            return parent;
        }

        public boolean isLeaf() {
            return childArray[0] == null;
        }

        public int getCountOfItems() {
            return nItems;
        }

        private Item<K, V> getItem(int index) {
            return itemArray[index];
        }

        private boolean isFull() {
            return nItems == ITEMS;
        }

        private int findItem(K key) {
            for (int i = 0; i < ITEMS; i++) {
                if (itemArray[i] == null) {
                    break;
                } else if (itemArray[i].getKey().compareTo(key) == 0) {
                    return i;
                }
            }
            return -1;
        }

        private int getIndexDuplicate(Item<K, V> item) {
            for (int i = 0; i < nItems; i++) {
                if (itemArray[i].getKey().compareTo(item.getKey()) == 0) {
                    return i;
                }
            }
            return -1;
        }

        private int insertItem(Item<K, V> item) {
            if (item == null) {
                throw new IllegalArgumentException("Argument in insertData method can't be null.");
            }
            int indexDuplicate = getIndexDuplicate(item);
            if (indexDuplicate==-1) {
                nItems++;
                for (int j = ITEMS - 1; j >= 0; j--) {
                    if (itemArray[j] == null) {
                        continue;
                    } else {
                        if (item.getKey().compareTo(itemArray[j].getKey()) < 0) {
                            itemArray[j + 1] = itemArray[j];
                        } else {
                            itemArray[j + 1] = item;
                            return j + 1;
                        }
                    }
                }
                itemArray[0] = item;
                return 0;
            }
            else {
                itemArray[indexDuplicate] = item;
                return indexDuplicate;
            }
        }

        private Item<K, V> removeLastItem() {
            Item<K, V> temp = itemArray[nItems - 1];
            itemArray[nItems - 1] = null;
            nItems--;
            return temp;
        }

        private void display() {
            for (int i = 0; i < nItems; i++) {
                itemArray[i].displayItem();
            }
            System.out.println("/");
        }
    }

    private static class Item<K extends Comparable<? super K>, V> {
        private K key;
        private V value;

        private Item(K key, V value) {
            this.value = value;
            this.key = key;
        }

        private K getKey() {
            return key;
        }

        private V getValue() {
            return value;
        }

        private void setKey(K key) {
            this.key = key;
        }

        private void setValue(V value) {
            this.value = value;
        }

        private void displayItem() {
            System.out.print("/" + value);
        }
//        public String toString(){
//            return key + ": "+ value+" ";
//        }
    }
}
