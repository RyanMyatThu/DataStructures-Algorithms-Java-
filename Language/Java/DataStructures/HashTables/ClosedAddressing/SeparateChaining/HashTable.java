package Language.Java.DataStructures.HashTables.ClosedAddressing.SeparateChaining;

import java.util.ArrayList;

public class HashTable<K,V>{
   private class HashNode<K,V>{
    K key;
    V val;
    HashNode next;
    int hashCodeVal;
    HashNode(K key, V val, int hashCodeVal){
        this.key = key;
        this.val = val;
        this.hashCodeVal = hashCodeVal;
    }
   }
  private ArrayList<HashNode<K,V>> bucketArray;
  private int numBucket;
  private int size = 0;

    private int hash(K key){
        if(key instanceof Integer integer){
            return integer;
        } else {
            String keyStr = "" + key;
            int n = keyStr.length();
            int hashCode = 0;
            int power = 1;
            for (int i = n - 1 ; i <= 0; i--){
                hashCode += keyStr.charAt(i) * power;
                power *= 31; 
            }
            return hashCode;
        }
    }
    private int getBucketIndex(K key){
        int rawHashKey = hash(key);
        int hashKey = rawHashKey < 0 ? rawHashKey * -1 : rawHashKey;
        int index = hashKey % numBucket;
        return index;
    }

    public HashTable() {
        bucketArray = new ArrayList<>();
        numBucket = 10;
        for (int i = 0; i < numBucket; i++) {
            bucketArray.add(null); // Empty out all elements with null initially
        }
    }

    public void add(K key, V val){
        if(key == null || val == null) return;
        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key);
        HashNode<K,V> newNode = new HashNode<>(key, val, hashCode);
        HashNode<K,V> head = bucketArray.get(bucketIndex);
        HashNode<K,V> prev = null;
        while(head != null){
            if(head.key.equals(key) && head.hashCodeVal == hashCode ){
                head.val = val;
                return;
            }
            prev = head;
            head = head.next;
        }
        size++;
        if(prev != null){
            prev.next = newNode;
        } else {
            newNode.next = head;
            bucketArray.set(bucketIndex, newNode);
        }

        // This code will be executed when Load factor is exceeded (Load Factor = 0.7 or 70% of the hashtable)
        if(( 1.0 * size  / numBucket) >= 0.7){
            ArrayList<HashNode<K,V>> temp = bucketArray;
            numBucket = 2 * numBucket;
            bucketArray = new ArrayList<>(numBucket);
            size = 0;
            for (int i = 0; i < numBucket; i++) {
            bucketArray.add(null); // Empty out all elements with null initially
            }   

            for(HashNode<K,V> headNode : temp){
                while(headNode != null){
                add(headNode.key, headNode.val);
                headNode = headNode.next;
                }
            }
        }
    }

    public V remove(K key){
        if(key == null) return null;
        if(isEmpty()) throw new RuntimeException("Hash table is empty");
        int bucketIndex = getBucketIndex(key);
        int code = hash(key);
        HashNode<K,V> head = bucketArray.get(bucketIndex);
        HashNode<K,V> prev = null;
        while(head != null){
            if(head.key.equals(key) && head.hashCodeVal == code){
                if(prev != null){
                    prev.next = head.next;
                    size--;
                    return head.val;
                } else {
                    bucketArray.set(bucketIndex,head.next);
                    size--;
                    return head.val;
                }
            }
            prev = head;
            head = head.next;
        }
        return null;
    }

    public V get(K key){
        if(isEmpty()) throw new RuntimeException("Hash table is empty");
        int bucketIndex = getBucketIndex(key);
        int code = hash(key);
        HashNode<K,V> head = bucketArray.get(bucketIndex);
        while(head != null){
            if(head.key.equals(key) && head.hashCodeVal == code){
                return head.val;
            }
            head = head.next;
        }
        return null;
    }
    
    public boolean isEmpty(){
        return size == 0;
    }

    public int getSize(){
        return size;
    }

   
   
}