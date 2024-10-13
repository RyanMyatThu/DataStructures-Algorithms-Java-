package Language.Java.DataStructures.HashTables.OpenAddressing.QuadraticProbing;

import java.util.ArrayList;

public class HashTable<K, V> {

    private class Entry {

        K key;
        V val;

        Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }

    }

    ArrayList<Entry> bucket = new ArrayList<>();
    int numBucket = 10;
    int size = 0;
    Entry dummy = new Entry(null, null);
    double loadFactor = 0.5;

    public HashTable() {
        for (int i = 0; i < numBucket; i++) {
            bucket.add(null);
        }
    }

    public void setLoadFactor(double loadFactor) {
        this.loadFactor = loadFactor;
    }

    public void add(K key, V val) {
        if (key == null || val == null) {
            return;
        }

        if ((double) size / numBucket >= loadFactor) {
            resize();
        }

        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key) & 0x7FFFFFFF;
        Entry occupied = bucket.get(bucketIndex);
        int n = 1;
        while (occupied != null) {
            if (occupied.key != null && occupied.key.equals(key)) {
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
    }

    public void remove(K key) {
        if (isEmpty()) {
            throw new RuntimeException("Hash table is empty");
        }
        if (key == null) {
            return;
        }

        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key) & 0x7FFFFFFF; //Positive hashCode
        Entry occupied = bucket.get(bucketIndex);
        int n = 1;

        while (occupied != null) {
            if (occupied.key != null && occupied.key.equals(key)) {
                bucket.set(bucketIndex, dummy);
                size--;
                return;
            }
            bucketIndex = (hashCode + n * n) % numBucket;
            occupied = bucket.get(bucketIndex);
            n++;
        }
    }

    public V get(K key) {
        if (key == null) {
            return null;
        }

        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key) & 0x7FFFFFFF;
        Entry occupied = bucket.get(bucketIndex);
        int n = 1;
        while (occupied != null) {
            if (occupied.key != null && occupied.key.equals(key)) {
                return occupied.val;
            }
            bucketIndex = (hashCode + n * n) % numBucket;
            occupied = bucket.get(bucketIndex);
            n++;
        }
        return null;
    }

    public boolean contains(K key) {
        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key) & 0x7FFFFFFF;
        Entry occupied = bucket.get(bucketIndex);
        int n = 1;
        while (occupied != null) {
            if (occupied.key != null && occupied.key.equals(key)) {
                return true;
            }
            bucketIndex = (hashCode + n * n) % numBucket;
            occupied = bucket.get(bucketIndex);
            n++;
        }
        return false;
    }

    public void printHashTable() {
        if (isEmpty()) {
            return;
        }
        for (int i = 0; i < numBucket; i++) {
            if (bucket.get(i) != null && bucket.get(i).key != null) {
                Entry temp = bucket.get(i);
                System.out.println("Key: " + temp.key + " | Value: " + temp.val + " | Index: " + i);
            }
        }
    }

    private void resize() {
        ArrayList<Entry> temp = bucket;
        size = 0;
        numBucket *= 2;
        bucket = new ArrayList<>(numBucket);
        for (int i = 0; i < numBucket; i++) {
            bucket.add(null);
        }
        for (Entry entry : temp) {
            if (entry != null && entry.key != null) {
                add(entry.key, entry.val);
            }
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private int getBucketIndex(K key) {
        int rawHashKey = hash(key);
        int hashKey = rawHashKey < 0 ? rawHashKey * -1 : rawHashKey;
        int index = hashKey % numBucket;
        return index;
    }

    private int hash(K key) {
        if (key instanceof Integer) {
            return (int) key;
        } else {
            int power = 1;
            int hashCode = 0;
            String toHash = key + "";
            int n = toHash.length();
            for (int i = n - 1; i >= 0; i--) {
                hashCode += toHash.charAt(i) * power;
                power *= 31;
            }
            return hashCode;

        }
    }
}
