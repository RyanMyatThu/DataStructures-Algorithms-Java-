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
        Tree tree = new Tree(3);
        tree.left = new Tree(2);
        tree.right = new Tree(1);
        traverse(tree);
    }
    
}
