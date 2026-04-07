package ds.test;

import ds.bst.BinarySearchTree;

public class BSTTest {

    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(50);
        bst.add(25);
        bst.add(75);
        bst.add(65);
        bst.add(67);


        System.out.println(bst);
        System.out.println(bst.remove(67));
        System.out.println(bst.debugLevelOrderString());
        System.out.println(bst.remove(67));
       }
}
