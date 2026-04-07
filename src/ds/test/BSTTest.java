package ds.test;

import ds.bst.BinarySearchTree;

public class BSTTest {

    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(50);
        bst.add(25);
        bst.add(15);
        bst.add(35);
        bst.add(75);
        bst.add(65);

        System.out.println(bst.debugLevelOrderString());
       }
}
