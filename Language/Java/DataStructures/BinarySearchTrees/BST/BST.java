package Language.Java.DataStructures.BinarySearchTrees.BST;

import java.util.ArrayList;
import java.util.Comparator;

public class BST<K,V> {

    public static class Entry<K,V>{
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }


    }    
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
    
    public V insert(K key, V value){
        root = put(root, key, value);
        return value;
    }

    private Node<K,V> put(Node<K,V> node, K key, V value){
        if(node == null){
            size++;
            return new Node<>(key, value);
        }
        int comp = comparator.compare(node.key, key);
        if(comp < 0){
            node.left = put(node.left, key, value);
        } else if (comp > 0){
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    public V remove(K key){
        Node<K,V> nodeToRemove = search(root, key);
        if(nodeToRemove == null){
            return null;
        }
        root = remove(root, key);
        size--;
        return nodeToRemove.value;
    }
    
    private Node<K,V> remove(Node<K,V> node, K key){
        if(node == null){
            return null;
        }
        int comp = comparator.compare(node.key, key);
        if(comp < 0){
            node.left = remove(node.left, key);
        } else if (comp > 0){
            node.right = remove(node.right, key);
        } else {
            if(node.left == null) return node.right;
            if(node.right == null) return node.left;
 
            node.key = findMin(node.right).key;
            node.value = findMin(node.right).value;
            node.right = remove(node.right, node.key);
        }
        return node;
    }

    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> entries = new ArrayList<>();
        inorderTraversal(root, entries);
        return entries;
    }

    private Node<K, V> findMin(Node<K, V> node) {
        while (node.left != null) node = node.left;
        return node;
    }
    
    private void inorderTraversal(Node<K, V> node, ArrayList<Entry<K, V>> entries) {
        if (node != null) {
            inorderTraversal(node.left, entries);
            entries.add(new Entry<>(node.key, node.value));
            inorderTraversal(node.right, entries);
        }
    }
    
}
