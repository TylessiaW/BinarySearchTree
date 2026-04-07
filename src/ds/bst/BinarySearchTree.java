package ds.bst;

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
            // add newData to the tree using parent
            int result = newData.compareTo(parent.data);

            if (result < 0) {
                parent.leftChild = new Node(newData);
            } else {
                parent.rightChild = new Node(newData);
            }

            return true;
        } else if (newData.compareTo(current.data) < 0) { // go left
            return add(current, current.leftChild, newData);
        } else if (newData.compareTo(current.data) > 0) { // go right
            return add(current, current.rightChild, newData);
        } else { // newData == current.data
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
        if(current == null) {
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

    private class Node {
        private Node leftChild, rightChild;
        private T data;

        public Node(T data) {
            this.data = data;
        }
    }
}