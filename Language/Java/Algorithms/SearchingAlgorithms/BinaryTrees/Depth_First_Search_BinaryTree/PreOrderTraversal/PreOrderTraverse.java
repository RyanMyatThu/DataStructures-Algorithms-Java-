package Language.Java.Algorithms.SearchingAlgorithms.BinaryTrees.Depth_First_Search_BinaryTree.PreOrderTraversal;

import Language.Java.Algorithms.SearchingAlgorithms.BinaryTrees.Tree;

public class PreOrderTraverse {
    public static void traverse(Tree root){
        if(root == null) return;
        System.out.println(root.data);
        traverse(root.left);
        traverse(root.right);
    }
    public static void main(String[] args) {
        Tree root = new Tree(3);  // Root node
        Tree node2 = new Tree(2);
        Tree node5 = new Tree(5);
        Tree node1 = new Tree(1);
        Tree node4 = new Tree(4);
        Tree node6 = new Tree(6);

        // Build the structure
        root.left = node2;
        root.right = node5;
        node2.left = node1;
        node5.left = node4;
        node5.right = node6;
        traverse(root);
    }
    
}
