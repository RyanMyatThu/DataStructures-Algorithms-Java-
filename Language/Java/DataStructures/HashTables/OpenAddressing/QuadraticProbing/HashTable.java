package Language.Java.DataStructures.HashTables.OpenAddressing.QuadraticProbing;

import java.util.ArrayList;

public class HashTable<K,V> {
    private class Entry{
        K key;
        V val;

        Entry(K key, V val){
            this.key = key;
            this.val = val;
        }
        
    }

    ArrayList<Entry> bucket = new ArrayList<>();
    int numBucket = 10;
    int size = 0;

    public HashTable() {
        for (int i = 0; i < numBucket; i++) {
            bucket.add(null);
        }
    }

    

    public void add(K key, V val){
        if(key == null || val == null) return;

        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key) & 0x7FFFFFFF;
        Entry occupied = bucket.get(bucketIndex);
        int n = 1;
        while(occupied != null){
            if(occupied.key.equals(key)){
                occupied.val = val;
                return;
            }
            bucketIndex = (hashCode + n * n) % numBucket;
            occupied = bucket.get(bucketIndex);
            n++;
        }
        Entry newEntry = new Entry(key, val);
        bucket.set(bucketIndex, newEntry);
        size++;

        if((double) size / numBucket >= 0.7){
            ArrayList<Entry> temp = bucket;
            size = 0;
            numBucket *= 2;
            bucket = new ArrayList<>(numBucket);
            for (int i = 0; i < numBucket; i++) {
                bucket.add(null);
            }
            for(Entry entry : temp){
                if(entry != null){
                add(entry.key, entry.val);
                }
            }
        }
    }

    public void remove(K key){
        if(key == null) return;
        if(!contains(key)) return;

        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key) & 0x7FFFFFFF; //Positive hashCode
        Entry occupied = bucket.get(bucketIndex);
        int n = 1;

        while(occupied != null){
            System.out.println(bucketIndex + " val: " + occupied.val + " target key: " + key);

            if(occupied.key.equals(key)){
                bucket.set(bucketIndex, null);
                size--;
                break;
            }
            bucketIndex = (hashCode + n * n) % numBucket;
            occupied = bucket.get(bucketIndex);
            n++;
        }

        n++;
        bucketIndex = (bucketIndex + 1) % numBucket;
        
        occupied = bucket.get(bucketIndex);
        
        for (int i = 0; i < numBucket; i++) {
            n++;
            if(occupied != null){
                Entry temp = occupied;
                K tempKey = temp.key;
                V tempVal = temp.val;
                bucket.set(bucketIndex, null);
                size--;
                add(tempKey,tempVal);
            }
            bucketIndex = (bucketIndex + n * n) % numBucket;
            occupied = bucket.get(bucketIndex);
        }
    }

    public V get(K key){
        if(key == null) return null;

        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key) & 0x7FFFFFFF;
        Entry occupied = bucket.get(bucketIndex);
        int n = 1;
        while(occupied != null){
            if(occupied.key.equals(key)){
                return occupied.val;
            }
            bucketIndex = (hashCode + n * n) % numBucket;
            occupied = bucket.get(bucketIndex);
            n++;
        }
        return null;
    }
    
    public boolean contains(K key){
        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key) & 0x7FFFFFFF;
        Entry occupied = bucket.get(bucketIndex);
        int n = 1;
        while(occupied != null){
            if(occupied.key.equals(key)){
                return true;
            }
            bucketIndex = (hashCode + n * n) % numBucket;
            occupied = bucket.get(bucketIndex);
            n++;
        }
        return false;
    }

    public void printHashTable(){
        if(isEmpty()) return;
        for (int i = 0; i < numBucket; i++) {
            if(bucket.get(i) != null){
                Entry temp = bucket.get(i);
                System.out.println("Key: " + temp.key + " | Value: " + temp.val + " | Index: " + i);
            }
        }
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    private int getBucketIndex(K key){
        int rawHashKey = hash(key);
        int hashKey = rawHashKey < 0 ? rawHashKey * -1 : rawHashKey;
        int index = hashKey % numBucket;
        return index;
    }
    private int hash(K key){
        if(key instanceof Integer){
            return (int) key;
        } else {
            int power = 1;
            int hashCode = 0;
            String toHash = key + "";
            int n =  toHash.length();
            for (int i = n - 1; i >= 0; i--) {
                hashCode += toHash.charAt(i) * power;
                power *= 31;
            }
            return hashCode;
            
        }
    }
}
