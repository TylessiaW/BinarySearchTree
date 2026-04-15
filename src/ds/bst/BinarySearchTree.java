package ds.bst;

import java.util.Queue;
import java.util.LinkedList;

public class BinarySearchTree<T extends Comparable<? super T>> {

    private Node root;
    private int size;

    public int getSize() {
        return size;
    }

    public boolean remove(T item) {
        if (isEmpty()) {
            return false;
        } else if (size == 1 && root.data.equals(item)) {
            clear();
            return true;
        } else if (removeTraversal(null, root, item)) {
            size -= 1;
            return true;
        } else {
            return false;
        }
    }

    private boolean removeTraversal(Node parent, Node current, T item) {
        boolean wasRemoved = false;
        if (current == null) {
            return false;
        } else if (item.compareTo(current.data) < 0) {
            wasRemoved = removeTraversal(current, current.leftChild, item);
        } else if (item.compareTo(current.data) > 0) {
            wasRemoved = removeTraversal(current, current.rightChild, item);
        } else {
            removeNode(parent, current);
            wasRemoved = true;
        }

        fixHeight(current);

        return wasRemoved;
    }

    private void removeNode(Node parent, Node current) {
        if (current.leftChild == null && current.rightChild == null) {
            removeCase1(parent, current);
        } else if (current.leftChild != null && current.rightChild == null) {
            removeCase2(parent, current);
        } else if (current.leftChild == null && current.rightChild != null) {
            removeCase3(parent, current);
        } else {
            removeCase4(current);
        }
    }

    // leaf node
    private void removeCase1(Node parent, Node current) {
        if (parent.leftChild == current) {
            parent.leftChild = null;
        } else {
            parent.rightChild = null;
        }
    }

    // only left child
    private void removeCase2(Node parent, Node current) {
        if(parent == null){
            root = root.leftChild;
        } else if (parent.leftChild == current) {
            parent.leftChild = current.leftChild;
        } else {
            parent.rightChild = current.leftChild;
        }

        current.leftChild = null;
    }

    // only right child
    private void removeCase3(Node parent, Node current) {
        if(parent == null){
            root = root.rightChild;
        } else if (parent.leftChild == current) {
            parent.leftChild = current.rightChild;
        } else {
            parent.rightChild = current.rightChild;
        }

        current.rightChild = null;
    }

    // two children
    private void removeCase4(Node current) {
        removeCase4(current, current, current.rightChild);
    }

    private void removeCase4(Node swapNode, Node parent, Node current) {
        if (current.leftChild == null) {
            swapNode.data = current.data;
            removeNode(parent, current);
        } else {
            removeCase4(swapNode, current, current.leftChild);
        }

        fixHeight(current);

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
        boolean wasAdded = false;
        if (current == null) {
            int result = newData.compareTo(parent.data);

            if (result < 0) {
                parent.leftChild = new Node(newData);
            } else {
                parent.rightChild = new Node(newData);
            }

            return true;
        } else if (newData.compareTo(current.data) < 0) {
            wasAdded = add(current, current.leftChild, newData);
        } else if (newData.compareTo(current.data) > 0) {
            wasAdded = add(current, current.rightChild, newData);
        } else {
            return false;
        }

        fixHeight(current);

        return wasAdded;
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

        return Math.max(current.leftHeight, current.rightHeight) + 1;
    }

    private void fixHeight(Node node) {
        if(node != null) {
            node.leftHeight = getHeight(node.leftChild);
            node.rightHeight = getHeight(node.rightChild);
        }
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

        String repr = "(LH: %dLC: %s | Me: %s | RH: %d RC: %s)";

        while (!queue.isEmpty()) {
            Node current = queue.remove();

            String leftChildData = (current.leftChild == null) ? "null" : current.leftChild.data.toString();
            String rightChildData = (current.rightChild == null) ? "null" : current.rightChild.data.toString();

            sb.append(String.format(repr, current.leftHeight, leftChildData, current.data, current.rightHeight, rightChildData));

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

        private int leftHeight, rightHeight;

        public Node(T data) {
            this.data = data;

            leftHeight = -1;
            rightHeight = -1;
        }
    }
}