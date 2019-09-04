package com.test.structureOfData.lafore.exercises.tree.treeBinary;

import java.util.HashMap;
import java.util.Stack;

import static com.test.structureOfData.lafore.exercises.tree.treeBinary.TraversalType.INFIX;
import static com.test.structureOfData.lafore.exercises.tree.treeBinary.TraversalType.POSTFIX;
import static com.test.structureOfData.lafore.exercises.tree.treeBinary.TraversalType.PREFIX;

/**
 * Exercise 8.4 from Robert Lafore Structures of data and algorithms
 */
public class StringArithmeticConverter {
    public static <K> String convertToArithmeticInfixForm(String s) {
        if (!isPotentialInfixArithmeticExpression(s)) {
            throw new IllegalArgumentException("Argument in convertToArithmeticInfixForm method isn't " +
                    "arithmetic expression.");
        }
        Node<K> root = getArithmeticInfixRoot(s);
        StringBuilder sb = new StringBuilder();
        getStringBuilderInInfixForm(root,sb);
        return sb.toString();
    }
    public static <K> double computeArithmeticExpressionInInfixForm(String s){
        if (!isPotentialInfixArithmeticExpression(s)) {
            throw new IllegalArgumentException("Argument in convertToArithmeticInfixForm method isn't " +
                    "arithmetic expression.");
        }
        Node<K> root = getArithmeticInfixRoot(s);
        double result = computeArithmeticExpression(root);
        return result;
    }

    private static <K> double computeArithmeticExpression(Node<K> node) {
        if (node.left == null){
            return Double.parseDouble(node.key.toString());
        }
        double left, right;
        left = computeArithmeticExpression(node.left);
        right = computeArithmeticExpression(node.right);

        String operation = node.key.toString();
        double result = 0;
        switch (operation){
            case "+":
                result = left+right; break;
            case "-":
                result = left-right; break;
            case "*":
                result = left*right; break;
            case "/":
                result = left/right; break;
            case "%":
                result = left%right; break;
        }
        return result;
    }


    private static boolean isPotentialArithmeticExpression(String s) {
        TraversalType form = getPotentialTraversalType(s);
        if (form != null) {
            return true;
        } else {
            return false;
        }
    }

    private static <K> Node<K> getArithmeticInfixRoot(String s) {
        HashMap<Integer, Integer> iBraces = new HashMap(s.length());
        Stack<Integer> stackBraces = new Stack<>();
        s = s.trim();
        String elements[] = new String[s.length()];
        int countE = 0;
        int lastI = 0;

        for (int i = 0; i < s.length(); i++) {
            char e = s.charAt(i);
            Character prev = null;
            if (i != 0) {
                prev = s.charAt(i - 1);
            }
            if (String.valueOf(e).matches("[-+*/%]")) {
                if (!String.valueOf(prev).matches("[)]")) {
                    elements[countE++] = s.substring(lastI, i).trim();
                }
                elements[countE++] = String.valueOf(e);
                lastI = i + 1;
            } else if (String.valueOf(e).matches("[(]")) {
                elements[countE++] = String.valueOf(e);
                lastI = i + 1;
                stackBraces.push(i);
            } else if (String.valueOf(e).matches("[)]")) {
                if (!String.valueOf(prev).matches("[)]")) {
                    elements[countE++] = s.substring(lastI, i).trim();
                }
                elements[countE++] = String.valueOf(e);
                lastI = i + 1;
                if (stackBraces.isEmpty()) {
                    throw new IllegalArgumentException("Argument in getPrefixArithmeticTreeBinary method" +
                            "not consists equal count of '(' and ')'.");
                }
                int iOpen = stackBraces.pop();
                int iClose = i;
                iBraces.put(iOpen, iClose);
            } else if (i == s.length() - 1 && !String.valueOf(prev).matches("[)]")) {
                elements[countE++] = s.substring(lastI, i + 1).trim();
            }
        }
        if (!stackBraces.isEmpty()) {
            throw new IllegalArgumentException("Argument in getPrefixArithmeticTreeBinary method " +
                    "not consists equal count of '(' and ')'.");
        }
        Node<K> root;
        try {
            root = getInfixRoot(0, countE - 1, elements, iBraces);
        }catch (Exception e){
            throw new IllegalArgumentException("" +
                    "Expression has not compliance with arithmetic forms or rules.");
        }
        assignParentAndIndexForAll(root, 0);
        return root;
    }

    private static <K> Node<K> getInfixRoot(int start, int finish, String[] elements, HashMap<Integer, Integer> iBraces) {
        Node<K> root = null;
        Node<K> current = null;
        for (int i = start; i <= finish; i++) {
            if (elements[i].matches("[-+]")) {
                Node<K> node = new Node(elements[i]);
                node.left = root;
                root = node;
                current = root;
            } else if (elements[i].matches("[*/%]")) {
                if (current != root){
                    K key = current.key;
                    Node<K> left = new Node(key);
                    current.left = left;
                    current.key = (K) elements[i];
                }
                else {
                    Node<K> node = new Node(elements[i]);
                    node.left = root;
                    root = node;
                    current = root;
                }
            } else if (elements[i].matches("[(]")) {
                if(root==null){
                    root = getInfixRoot(i + 1, iBraces.get(i) - 1, elements, iBraces);
                    current = root;
                }
                else {
                    current.right = getInfixRoot(i + 1, iBraces.get(i) - 1, elements, iBraces);
                }
                i = iBraces.get(i);
            } else {
                Node<K> right = new Node(elements[i]);
                if (root == null) {
                    root = right;
                    current = root;
                }
                else {
                    current.right = right;
                    current = right;
                }
            }
        }
        return root;
    }

    private static <K> void assignParentAndIndexForAll(Node<K> node, int index) {
        if (node == null){
            return;
        }
        if (node.left !=null){
            node.left.parent = node;
        }
        if (node.right !=null){
            node.right.parent = node;
        }
        assignParentAndIndexForAll(node.left, index*2+1);
        node.index = index;
        assignParentAndIndexForAll(node.right, index*2+2);
    }

    private static TraversalType getPotentialTraversalType(String s) {
        if (isPotentialPrefixArithmeticExpression(s)) {
            return PREFIX;
        }
        if (isPotentialInfixArithmeticExpression(s)) {
            return INFIX;
        }
        if (isPotentialPostfixArithmeticExpression(s)) {
            return POSTFIX;
        }
        return null;
    }

    private static boolean isPotentialPrefixArithmeticExpression(String s) {
        s = s.trim();
        char first = s.charAt(0);
        char last = s.charAt(s.length() - 1);
        if (!String.valueOf(first).matches("[-+*/%]")) {
            return false;
        }
        if (String.valueOf(last).matches("[-+*/%]") ||
                String.valueOf(last).matches("[)]")) {
            return false;
        }
        return true;
    }

    private static boolean isPotentialInfixArithmeticExpression(String s) {
        s = s.trim();
        char first = s.charAt(0);
        char last = s.charAt(s.length() - 1);
        if (String.valueOf(first).matches("[-+*/%]")) {
            return false;
        }
        if (String.valueOf(last).matches("[-+*/%]")) {
            return false;
        }
        return true;
    }

    private static boolean isPotentialPostfixArithmeticExpression(String s) {
        s = s.trim();
        char first = s.charAt(0);
        char last = s.charAt(s.length() - 1);
        if (String.valueOf(first).matches("[-+*/%]") ||
                String.valueOf(last).matches("[(]")) {
            return false;
        }
        if (!String.valueOf(last).matches("[-+*/%]")) {
            return false;
        }
        return true;
    }

    private static <K> void getStringBuilderInInfixForm(Node<K> node, StringBuilder sb) {
        if (node != null) {
            getStringBuilderInInfixForm(node.left, sb);
            if (node.parent != null && node.parent.parent != null) {
                if (node.parent.left == node &&
                        node.parent.key.toString().matches("[-+]") &&
                        node.parent.parent.key.toString().matches("[*/%]")) {
                    sb.append("(");
                }
            }
            sb.append(node.key);
            if (node.parent != null && node.parent.parent != null) {
                if (node.parent.right == node &&
                        node.parent.key.toString().matches("[-+]") &&
                        node.parent.parent.key.toString().matches("[*/%]")) {
                    sb.append(")");
                }
            }
            getStringBuilderInInfixForm(node.right, sb);
        }
    }
    private static class Node<K> {
        private K key;
        private Node<K> parent, left, right;
        private int index;

        public Node(K key) {
            this.key = key;
        }

        public void displayNode() {
            System.out.print('{' + key.toString() + ", " + "} ");
        }
    }
}
