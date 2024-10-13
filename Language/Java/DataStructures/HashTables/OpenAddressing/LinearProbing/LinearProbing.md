# Linear Probing In A hash Table

---

## What Is Linear Probing?

Linear Probing is a collision resolution technique used in hash tables. It is one of the three techniques included in `Open Addressing` method. Here are the other techniques : 

1. Linear Probing
2. Quadratic Probing
3. Double Hashing

> Whenever a collision is encountered while determining the index of an element, Linear Probing will search the hash table for an empty space *linearly*.

---

## How Does Linear Probing Work?

To implement a hash table that handles collisions using linear probing we would need to consider traversing the hash table in a pattern. Particularly, the following pattern would be used to search for empty space: 

```
n = 1;
(Hash(key) + n) % the allocated size of the hash table
n++ (n is incremented)
```
> Due to its design, linear probing hash tables would face clustering as the number of elements rise in the hash table.
---

## Implementing The Hash Table

**The Inner Entry Class :**

```Java
  private class Entry{
        K key;
        V val;
        Entry(K key, V val){
            this.key = key;
            this.val = val;
        }
    }
```

**The Hash Table Class :**

```Java
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
```

**Attributes Of The Hash Table Class :**

```Java
    Entry[] bucket = (Entry[]) Array.newInstance(Entry.class, 10);
    int numBucket = 10; // default
    int size = 0;
    Entry dummy = new Entry(null, null);
    double loadFactor = 0.5;
```

Here we could see: 

1. The array `bucket` containing our entries.
2. Variable `numBucket` to store the allocated size for our hash table (default is 10)
3. Variable `size` to indicate how much elements we have inserted in our hash table.
4. An instance of `Entry` called `dummy`; it will be used to replace the `removed` nodes during the removal process.
5. Variable `loadFactor`, it will be utilized in the process of resizing our hash table when the `size` is equal to or greater than our `loadFactor`.

---

**Insertion Method**

1. `add(K key, V val)`

The method takes in two arguments `key` and `val`. As a hash table, `null` values or keys are not allowed.

**Code Preview :**

```Java
 public void add(K key, V val) {
        if (key == null || val == null) {
            return; // null values or keys aren't allowed
        }

        if ((double) size / numBucket >= loadFactor) {
            resize(); // call the resize method whenever size reaches or exceeds the load factor
        }

        int bucketIndex = getBucketIndex(key);
        int hashCode = hash(key) & 0x7FFFFFFF; // Make sure our hash code is positive
        Entry occupied = bucket[bucketIndex];
        Entry newEntry = new Entry(key, val);
        int n = 1;
        while (occupied != null) {
            if (occupied.key != null && occupied.key.equals(key)) {
                bucket[bucketIndex].val = val;
                return;
            }
            bucketIndex = (hashCode + n) % numBucket; // Linear probing
            n++; // Increment n
            occupied = bucket[bucketIndex]; 
        }

        bucket[bucketIndex] = newEntry;
        size++;
    }
```

When a key gets passed into the method as an argument, it will be used to compute the index of the element. We will also store the hash code of the key in `hashCode` so we could use it in our probing sequence. `occupied` is used to store the `Entry` object that is occupying the space of the computed bucket index. [^1]
If `occupied` is not an empty space, and if the `key` of `occupied` equals to our inserted `key` we would update the entry with the new value. If collision occurs, we will compute our next index using linear probing. We will repeat the probing process until an empty space is found. 

---

## Removal Method

2. `remove(K key)`


**Code Preview :**

```java
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
                size--; // Decrease the size
                return;
            }
            bucketIndex = (hashCode + n) % numBucket;
            occupied = bucket[bucketIndex];
            n++;
        }

    }
```

Here, we would calculate the index of our **element to be removed** using `getBucketIndex(...)`. Store the hash code of our key in `hashCode` to use in our linear probing sequence. If the element is found directly, we would mark that index as **Deleted** by replacing the current element with a `dummy` object. This process is called lazy deletion. [^2] If the element is not found directly, we will search for the element using our linear probing process. 

---

## Get Method

3. `get(K key)`


**Code Preview :**

As the name suggests, this method will search for the entry with the provided key.

```java
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
```

---

## Contains Method

4. `contains(K key)`

The following method will search for the entry with the provided key. If it exists it will return `true` otherwise, it will return `false`.

**Code Preview :**

```java
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
```

---

## Display The Hash Table

5. `printHashTable()`

The method will print out the `key` and `val` of each entry (excluding the dummy entries).

**Code Preview :**

```java
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
```
---

## Check If Empty

6. `isEmpty()`

```java
public boolean isEmpty() {
        return size == 0;
    }
```

---

## Get Size Of Hash Table

7. `size()`

```java
public int size() {
        return size;
    }
```

---

## Utility Methods

1. `resize()`
2. `getBucketIndex()`
3. `hash()`

**Code Preview For Resize :**

```java
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
```
It will double the allocated size of the hash table. Then store the previous state of our `bucket` in `temp`. We will create a new instance of our array of entries with the doubled size. When adding the previous entries we won't include the `dummy` entries. Therefore, optimizing our hash table's performance.

**Code Preview For Getting Bucket Index :**

```java
 private int getBucketIndex(K key) {
        int rawHashKey = hash(key);
        int hashKey = rawHashKey < 0 ? rawHashKey * -1 : rawHashKey; // Positive hash key
        int index = hashKey % numBucket;
        return index;
    }
```

This will receive a hash key from `hash(...)` then perform a modulo operation to get the index.

---

## Time Complexity 

|  Operation    |  Time Complexity     |  
|   ---         | ---                  | 
| Insertion     | O(1)                 | 
| Deletion      | O(1)                 |
| Search        | O(1)                 |

> Note: Although in the best case, the operations will perform in constant time but if there are collisions, it becomes O(n).


[^1]: If `occupied` is `null` it means that the space in the hash table is empty and the `entry` that we want to add can be placed in that certain `bucketIndex`. A collision occurs when `occupied` is not null.

[^2]: Lazy deletion is easy and simple to implement, but the hash table's performance will degrade over time. 