package Language.Java.DataStructures.HashTables.OpenAddressing.LinearProbing;

import java.lang.reflect.Array;


public class HashTable <K,V> {
    private class Entry{
        K key;
        V val;
        int hashCode;
        Entry(K key, V val, int hashCode){
            this.key = key;
            this.val = val;
            this.hashCode = hashCode;
        }
    }


    Entry[] bucket = (Entry[]) Array.newInstance(Entry.class, 10);
    int numBucket = 10; // default
    int size = 0;

    public void add(K key, V val){
        if(key == null || val == null) return;
        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key);
        Entry occupied = bucket[bucketIndex];
        Entry newEntry = new Entry(key,val,hashCode);
            if(occupied != null){
                if(occupied.key.equals(key)){
                    occupied.val = val;
                    return;
                } else {
                    int n = 1;
                    while(occupied != null){
                          bucketIndex = (hashCode + n) % numBucket;
                          occupied = bucket[bucketIndex]; 
                          n++;
                    }
                }
            } 

        bucket[bucketIndex] = newEntry;
        size++;
        // Load factor and resizing 

        if((double) size / numBucket >= 0.7){
            numBucket = numBucket * 2;
            size = 0;
            Entry[] temp = bucket;
            bucket = (Entry[]) Array.newInstance(Entry.class, numBucket);
            for (Entry entry : temp) {
                if(entry != null){
                 add(entry.key, entry.val);
                }
            }
        }
    }

    public void remove(K key){
        if(!contains(key)) {
            return;
        }

        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key);
        Entry occupied = bucket[bucketIndex];
        int n = 1;
        while(occupied != null){
            if(occupied.key.equals(key)){
                bucket[bucketIndex] = null;
                size--;
                break;
            }
            bucketIndex = (hashCode + n) % numBucket;
            occupied = bucket[bucketIndex];
            n++; 
        }
        
       
        bucketIndex = (bucketIndex + 1) % numBucket;
        occupied = bucket[bucketIndex];
        while(occupied != null){
            Entry temp = occupied;
            K tempKey = temp.key;
            V tempVal = temp.val;
            bucket[bucketIndex] = null;
            size--;
            add(tempKey,tempVal);
            bucketIndex = (bucketIndex + 1) % numBucket;
            occupied = bucket[bucketIndex];
        }
    }

    public V get(K key){
        if(key == null) return null;

        if(isEmpty()) throw new RuntimeException("Empty hash table");
        int bucketIndex =getBucketIndex(key);
        int hashCode = hash(key);
        Entry occupied = bucket[bucketIndex];
        int n = 1;
        while(occupied != null){
            if(occupied.key.equals(key)){
                return occupied.val;
            }
            bucketIndex = (hashCode + n) % numBucket;
            occupied = bucket[bucketIndex];
            n++;
        }
        return null;
    }

    public boolean contains(K key){
        if(key == null) return false;

        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key);
        Entry occupied = bucket[bucketIndex];
        int n = 1;
        while(occupied != null){
            if(occupied.key.equals(key)){
                return true;
            }
            bucketIndex = (hashCode + n) % numBucket;
            occupied = bucket[bucketIndex];
            n++;
        }
        return false;
    }

    private int getBucketIndex(K key){
        if(key == null) throw new RuntimeException("No null values allowed");

        int hashCode = hash(key);
        return hashCode % numBucket;
    }

    private int hash(K key){
        if(key == null) throw new RuntimeException("No null values allowed");

        if(key instanceof Integer){
            return (int) key;
        } else {
            int power = 1;
            int hashCode = 0;
            String stringKey = key + "";
            int n = stringKey.length();

            for (int i = n - 1; i >= 0; i--) {
                hashCode += stringKey.charAt(i) * power;
                power *= 31;
            }
            return hashCode;
        }
    }

    public void printHashTable(){
        if(isEmpty()) return;
        for(Entry cur : bucket) {
            if(cur != null){
                System.out.println("Key: " + cur.key + " | Value: " + cur.val);
            }
        }
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    } 
}
