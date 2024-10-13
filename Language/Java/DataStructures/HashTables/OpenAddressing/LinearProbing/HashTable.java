package Language.Java.DataStructures.HashTables.OpenAddressing.LinearProbing;

import java.lang.reflect.Array;

public class HashTable<K, V> {

    private class Entry {

        K key;
        V val;

        Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    Entry[] bucket = (Entry[]) Array.newInstance(Entry.class, 10);
    int numBucket = 10; // default
    int size = 0;
    Entry dummy = new Entry(null, null);
    double loadFactor = 0.5;

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
        Entry occupied = bucket[bucketIndex];
        Entry newEntry = new Entry(key, val);
        int n = 1;
        while (occupied != null) {
            if (occupied.key != null && occupied.key.equals(key)) {
                bucket[bucketIndex].val = val;
                return;
            }
            bucketIndex = (hashCode + n) % numBucket;
            n++;
            occupied = bucket[bucketIndex];
        }

        bucket[bucketIndex] = newEntry;
        size++;
    }

    public void remove(K key) {
        if (key == null) {
            return;
        }
        if (isEmpty()) {
            throw new RuntimeException("Hash table is empty");
        }

        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key) & 0x7FFFFFFF;
        Entry occupied = bucket[bucketIndex];
        int n = 1;
        while (occupied != null) {
            if (occupied.key != null && occupied.key.equals(key)) {
                bucket[bucketIndex] = dummy;
                size--;
                return;
            }
            bucketIndex = (hashCode + n) % numBucket;
            occupied = bucket[bucketIndex];
            n++;
        }

    }

    public V get(K key) {
        if (key == null) {
            return null;
        }

        if (isEmpty()) {
            throw new RuntimeException("Empty hash table");
        }
        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key) & 0x7FFFFFFF;
        Entry occupied = bucket[bucketIndex];
        int n = 1;
        while (occupied != null) {
            if (occupied.key != null && occupied.key.equals(key)) {
                return occupied.val;
            }
            bucketIndex = (hashCode + n) % numBucket;
            occupied = bucket[bucketIndex];
            n++;
        }
        return null;
    }

    public boolean contains(K key) {
        if (key == null) {
            return false;
        }
        if (isEmpty()) {
            throw new RuntimeException("Hash table is empty");
        }

        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key) & 0x7FFFFFFF;
        Entry occupied = bucket[bucketIndex];
        int n = 1;
        while (occupied != null) {
            if (occupied.key != null && occupied.key.equals(key)) {
                return true;
            }
            bucketIndex = (hashCode + n) % numBucket;
            occupied = bucket[bucketIndex];
            n++;
        }
        return false;
    }

    private void resize() {

        numBucket = numBucket * 2;
        size = 0;
        Entry[] temp = bucket;
        bucket = (Entry[]) Array.newInstance(Entry.class, numBucket);
        for (Entry entry : temp) {
            if (entry != null && entry.key != null) {
                add(entry.key, entry.val);
            }
        }

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
            String stringKey = key + "";
            int n = stringKey.length();

            for (int i = n - 1; i >= 0; i--) {
                hashCode += stringKey.charAt(i) * power;
                power *= 31;
            }
            return hashCode;
        }
    }

    public void printHashTable() {
        if (isEmpty()) {
            return;
        }
        for (Entry cur : bucket) {
            if (cur != null && cur.key != null) {
                System.out.println("Key: " + cur.key + " | Value: " + cur.val);
            }
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
