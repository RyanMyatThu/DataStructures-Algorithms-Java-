package Language.Java.DataStructures.BinarySearchTrees.BST;

import java.util.Comparator;

public class BST<K,V> {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Node<K, V> root;
    private Comparator<K> comparator;
    private int size;
    
    public BST() {
        this.comparator = null;
        this.size = 0;
    }

    public BST(Comparator<K> comparator) {
        this.comparator = comparator;
        this.size = 0;
    }
    
    private int compare(K key1, K key2){
        if(comparator != null) {
            return comparator.compare(key1, key2);
        }
        return ((Comparable<K>) key1).compareTo(key2);
    }

    private Node<K,V> search(Node<K,V> node,K key){
        if(node == null) return null;
        int comp = comparator.compare(node.key, key);
        if(comp == 0) return node;
        if(comp < 0) return search(node.left, key);
        else return search(node.right, key);
    }
    
    
}
