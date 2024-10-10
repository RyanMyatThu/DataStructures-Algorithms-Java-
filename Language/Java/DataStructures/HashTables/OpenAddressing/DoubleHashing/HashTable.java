package Language.Java.DataStructures.HashTables.OpenAddressing.DoubleHashing;

import java.util.ArrayList;
import java.util.BitSet;

public class HashTable <K,V> {
    private class Entry{
        K key;
        V val;
        Entry(K key, V val){
            this.key = key;
            this.val = val;
        }
    }
    private static final long MAX_SIZE = 10000001L; 
    BitSet isPrime;
    ArrayList<Entry> bucket = new ArrayList<>();
    Entry dummy = new Entry(null , null);
    int numBucket;
    double loadFactor = 0.5;
    int prime;
    int size;

    public HashTable(int n, double loadFactor) {
       isPrime = new BitSet((int) MAX_SIZE);
       setSieve();
       numBucket = n;
       this.loadFactor = loadFactor;
       prime = numBucket - 1;
       while(isPrime.get(prime)) prime--;
       size = 0;
       for(int i = 0 ; i < numBucket; i++){
        bucket.add(null);

       }
    }

   private void setSieve(){
    isPrime.set(0, true);
    isPrime.set(1, true);
    for (long i = 2; i * i < MAX_SIZE; i++) {
        if(!isPrime.get((int) i)){
            for(long j = i * i; j <= MAX_SIZE; j += i){
                isPrime.set((int)j , true);
            }
        }
        
    }

   }
   private int genericHash(K key) {
    if (key == null) return 0;
    
    
    if(key instanceof Integer) {
        return Math.abs(((int) key) % numBucket);
    } else if (key instanceof String) {
        return Math.abs(key.hashCode() % numBucket);
    } else if (key instanceof Double || key instanceof Float) {
        return Math.abs(Double.valueOf( (double) key).hashCode());
    } else {
        // Fallback for custom objects - use Java's built-in hashCode
     return Math.abs(key.hashCode() % numBucket);
    }
    }

    public void add(K key, V val){
        if(key == null || val == null) return;

        if((double) size / numBucket >= loadFactor){
            resize();
        }

        int probe = hash1(key), offset = hash2(key);
        while(bucket.get(probe) != null){
            if(bucket.get(probe).key != null &&  bucket.get(probe).key.equals(key)){
                bucket.get(probe).val = val;
                return;
            }
            probe = (probe + offset) % numBucket; 
        }
        bucket.set(probe, new Entry(key,val));
        size++;
    }

    public void remove(K key){
        if(key == null) return;
        int probe = hash1(key), offset = hash2(key);
        while(bucket.get(probe) != null){
            if(bucket.get(probe).key != null && bucket.get(probe).key.equals(key)){
                bucket.set(probe, dummy);
                size--;
            }
            probe = (probe + offset) % numBucket;
        }
    }

    public V get(K key){
        if(key == null) throw new RuntimeException("Null keys not accepted");
        int probe = hash1(key), offset = hash2(key);
        while(bucket.get(probe) != null){
            if(bucket.get(probe).key != null && bucket.get(probe).key.equals(key)){
                return bucket.get(probe).val;
            }
            probe = (probe + offset) % numBucket;
        }
        return null;
    }
    
    public boolean contains(K key) {
        if (key == null) throw new RuntimeException("Null keys not accepted");
        int probe = hash1(key), offset = hash2(key);
        while (bucket.get(probe) != null) {
            if (bucket.get(probe) != dummy && bucket.get(probe).key.equals(key)) {
                return true;
            }
            probe = (probe + offset) % numBucket;
        }
        return false;
    }

    public void printHashTable(){
        if(isEmpty()) return;
        for(Entry cur : bucket) {
            if(cur != null && cur.key != null){
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

    private void resize(){
        ArrayList<Entry> temp = bucket;
        numBucket *= 2;
        prime = numBucket - 1;
        bucket = new ArrayList<>(numBucket);
        size = 0;
        for(int i = 0; i < numBucket; i++){
            bucket.add(null);
        }
        for(Entry entry : temp){
            if(entry != null && entry.key != null){
                add(entry.key,entry.val);
            }
        }
    }

    private int hash1(K key) {
        return genericHash(key);
    }

   
    private int hash2(K key) {
    int hash = genericHash(key);
    return prime - (hash % prime);
    }

    
}
