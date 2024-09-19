package Language.Java.Algorithms.SearchingAlgorithms.BinaryTrees.Depth_First_Search_BinaryTree.InOrderTraversal;

import Language.Java.Algorithms.SearchingAlgorithms.BinaryTrees.Depth_First_Search_BinaryTree.Tree;

public class InOrderTraverse {
    public static void traverse(Tree root){
        if(root == null) return;
        traverse(root.left);
        System.out.println(root.data);
        traverse(root.right);
    }
    public static void main(String[] args) {
        Tree tree = new Tree(3);
        tree.left = new Tree(2);
        tree.right = new Tree(1);
        traverse(tree);
    }

}
