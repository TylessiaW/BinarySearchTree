package ds.bst;

import java.util.Queue;
import java.util.LinkedList;

public class BinarySearchTree<T extends Comparable<? super T>> {

    private Node root;
    private int size;

    public int getSize() {
        return size;
    }

    public boolean add(T newData) {
        boolean wasAdded = false;

        if (isEmpty()) {
            root = new Node(newData);
            wasAdded = true;
        } else {
            wasAdded = add(null, root, newData);
        }

        if (wasAdded) {
            size += 1;
        }

        return wasAdded;
    }

    private boolean add(Node parent, Node current, T newData) {
        if (current == null) {
            int result = newData.compareTo(parent.data);

            if (result < 0) {
                parent.leftChild = new Node(newData);
            } else {
                parent.rightChild = new Node(newData);
            }

            return true;
        } else if (newData.compareTo(current.data) < 0) {
            return add(current, current.leftChild, newData);
        } else if (newData.compareTo(current.data) > 0) {
            return add(current, current.rightChild, newData);
        } else {
            return false;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node current) {
        if (current == null) {
            return -1;
        }

        return Math.max(getHeight(current.leftChild), getHeight(current.rightChild)) + 1;
    }

    @Override
    public String toString() {
        return inOrderString();
    }

    public String inOrderString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (!isEmpty()) {
            inOrderString(root, sb);
        }
        sb.append("}");
        return sb.toString();
    }

    private void inOrderString(Node current, StringBuilder sb) {
        if (current != null) {
            inOrderString(current.leftChild, sb);

            if (sb.length() > 1) {
                sb.append(", ");
            }

            sb.append(current.data);

            inOrderString(current.rightChild, sb);
        }
    }

    public String debugLevelOrderString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DEBUG {");

        if (!isEmpty()) {
            debugLevelOrderString(sb);
        }

        sb.append("}");
        return sb.toString();
    }

    private void debugLevelOrderString(StringBuilder sb) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        String repr = "(LC: %s | Me: %s | RC: %s)";

        while (!queue.isEmpty()) {
            Node current = queue.remove();

            String leftChildData = (current.leftChild == null) ? "null" : current.leftChild.data.toString();
            String rightChildData = (current.rightChild == null) ? "null" : current.rightChild.data.toString();

            sb.append(String.format(repr, leftChildData, current.data, rightChildData));

            if (current.leftChild != null) {
                queue.add(current.leftChild);
            }

            if (current.rightChild != null) {
                queue.add(current.rightChild);
            }

            if (!queue.isEmpty()) {
                sb.append(", ");
            }
        }
    }

    private class Node {
        private Node leftChild, rightChild;
        private T data;

        public Node(T data) {
            this.data = data;
        }
    }
}