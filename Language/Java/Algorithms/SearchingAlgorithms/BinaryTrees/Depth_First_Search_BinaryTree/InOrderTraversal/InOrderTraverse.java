package Language.Java.Algorithms.SearchingAlgorithms.BinaryTrees.Depth_First_Search_BinaryTree.InOrderTraversal;

import Language.Java.Algorithms.SearchingAlgorithms.BinaryTrees.Depth_First_Search_BinaryTree.Tree;

public class InOrderTraverse {
    public static void traverse(Tree root){
        if(root == null) return;
        traverse(root.left);
        System.out.println(root.data);
        traverse(root.right);
    }
    public static void main(String[] akrgs) {
        Tree first = new Tree(1);
        Tree second = new Tree(2);
        Tree third = new Tree(3);
        Tree fourth = new Tree(4);
        Tree fifth = new Tree(5);
        Tree sixth = new Tree(6);
        Tree seventh = new Tree(7);
        Tree eigtht = new Tree(8);
        Tree ninth = new Tree(9);

        first.right = third;
        first.left = second;
        second.left = fourth;
        third.right = fifth;
        fourth.left = sixth;
        fourth.right = seventh;
        fifth.left = eigtht;
        fifth.right = ninth;

        traverse(first);
    }

}
