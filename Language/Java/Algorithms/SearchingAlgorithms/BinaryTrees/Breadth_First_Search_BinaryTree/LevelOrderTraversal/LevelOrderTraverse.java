package Language.Java.Algorithms.SearchingAlgorithms.BinaryTrees.Breadth_First_Search_BinaryTree.LevelOrderTraversal;

import Language.Java.Algorithms.SearchingAlgorithms.BinaryTrees.Tree;
import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraverse {
    public static void traverse(Tree root){
        Queue<Tree> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            Tree current = queue.remove();
            System.out.println(current.data);
            if(current.left != null){
            queue.add(current.left);
            }
            if(current.right != null){
            queue.add(current.right);
            }
        }
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
