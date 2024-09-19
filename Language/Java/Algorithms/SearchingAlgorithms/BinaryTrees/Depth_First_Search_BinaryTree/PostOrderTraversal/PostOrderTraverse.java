package Language.Java.Algorithms.SearchingAlgorithms.BinaryTrees.Depth_First_Search_BinaryTree.PostOrderTraversal;

import  Language.Java.Algorithms.SearchingAlgorithms.BinaryTrees.Depth_First_Search_BinaryTree.Tree;

public class PostOrderTraverse {
    public static void traverse(Tree root){
        if(root == null) return;
        traverse(root.left);
        traverse(root.right);
        System.out.println(root.data);
        
    }
    public static void main(String[] args) {
        Tree tree = new Tree(3);
        tree.left = new Tree(2);
        tree.right = new Tree(1);
        traverse(tree);
    }

}
