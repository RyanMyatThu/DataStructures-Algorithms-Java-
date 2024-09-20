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
