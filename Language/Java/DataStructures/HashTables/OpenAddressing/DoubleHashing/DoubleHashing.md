# Double Hashing In Hash Tables
---

## What is Double Hashing?

Double hashing is an open addressing technique used in hash tables to resolve collisions. It's one of the most effective methods for handling collisions, providing better distribution and minimizing clustering compared to linear probing and quadratic probing.

Here are the open addressing techniques :

1. Linear Probing
2. Quadratic Probing
3. Double Hashing

> If a hash table uses double hashing technique to handle collisions, it will require two hash functions.

---

## How Does Double Hashing Work?

Double hashing uses two hash functions:
1. The primary hash function h1(key) to compute the initial index.
2. A secondary hash function h2(key) to determine the step size for probing.

When a collision occurs, the next probe position is calculated using the formula:

```
int probe = hash1(key), offset = hash2(key);

probe = (probe + offset) % numBucket

```

---

## Implementing A Hash Table Using Double Hashing

**The Inner Class :**

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

    public HashTable(int n) {
       isPrime = new BitSet((int) MAX_SIZE);
       setSieve();
       numBucket = n;
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
```

## Attributes Of The Hash Table Class

``` java
    private static final long MAX_SIZE = 10000001L;  // Max size in long
    BitSet isPrime;
    ArrayList<Entry> bucket = new ArrayList<>();
    Entry dummy = new Entry(null , null);
    int numBucket;
    double loadFactor = 0.5;
    int prime;
    int size;
```

- `MAX_SIZE` will be used to calculate the `prime` number; which is used in the second hash calculation.
- The `BitSet`, `isPrime` is used to determine the prime numbers that will be used; by utilizing a particular algorithm called **Sieve Of Eratosthenes** 
- `dummy` will be the dummy instance of `Entry` class which will be used to replace the deleted nodes during deletion process.
- `numBucket` indicates the allocated space of the hashtable.
- `prime` will be the prime number that will be used in the second hash calculation.
- `size` will be the total number of elements we added in the hashtable.
---

**Initialization Processes**

We will be setting our primes using a `BitSet` object. We will name it `isPrime`. Whenever our hash table is instantiated, we will call  our `setSieve()` method, which uses the **Sieve Of Eratosthenes** algorithm. After the setting of prime numbers in our bitset, we will assign the provided capacity `n` to `numBucket`. We will also set the value of load factor using the provided value. If the instantiation used an overload constructure without providing the value of `loadFactor` it will be `0.5` on default. Next, we will find the value of `prime`.

First we will calculate `prime` by subtracting `1` from `numBucket`. We will check if the result is prime or not using our `BitSet`. We will decrement the value of `prime` by 1 until it becomes a prime number.

**Code Preview :**

```Java
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

    //Overloaded constructor
    public HashTable(int n) {
       isPrime = new BitSet((int) MAX_SIZE);
       setSieve();
       numBucket = n;
       prime = numBucket - 1;
       while(isPrime.get(prime)) prime--;
       size = 0;
       for(int i = 0 ; i < numBucket; i++){
        bucket.add(null);

       }
    }
```
 
>To further understand about the Sieve Of Eratosthenes, you can proceed to the algorithms section.



---

## Insertion Method

1. `add(K key, V val)`

As the name suggests, this method will insert an entry with the provided key and value. If a collision occurs, it will use double hashing to find an empty space in the hash table. 

**Code Preview :**

```Java
public void add(K key, V val){
        if(key == null || val == null) return; // null values or keys aren't allowed

        if((double) size / numBucket >= loadFactor){
            resize(); // If our total number of elements is equal or greater than our load factor, we will resize our hash table.
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
```

The variable `probe` would be the result from the first hash function `hash1()` and the variable `offset` would be the result from the second hash function called `hash2()`. When combined, we would get the next index to see if its empty or not.

> While adding entries, we will be actively checking if our total number of entries is equal to or greater than the loadfactor. If it is, we will call the method resize(). Otherwise, we will proceed to adding the entry.

---

## Removal Method

The following method will deal with removing a given entry from a hash table.

```Java
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
```

This method uses the same probing sequence as the previous. It will search for the entry having the same key as the provided. If found, the entry will be replaced with a dummy entry and size would be decremented.

---

## Get Method

This method will search for an entry with the provided key. Upon reaching that particular entry, we will return the `val` of that entry. If the entry is not found, we will instead return `null`.

**Code Preview :**

```Java
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
```
---

## Contains Method

This is similar to our `get` method, but instead of returning the value, this will return a `boolean` value. It returns `true` if the entry is found. If the entry isn't found it returns `false`.

**Code Preview :**

```Java

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
```

---

## Print HashTable Method

This method will iterate through our hashtable skipping through dummy nodes and printing out the key and value pairs.

```java

public void printHashTable(){
        if(isEmpty()) return;
        for(Entry cur : bucket) {
            if(cur != null && cur.key != null){
                System.out.println("Key: " + cur.key + " | Value: " + cur.val);
            }
        }
    }
```

---

## Double Hash Functions

**Hash Function Number 1 :**

```Java
private int hash1(K key) {
        return genericHash(key);
    }
```

**Generic Hash :**

```Java
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
```

**Hash Function Number 2:**

```Java
private int hash2(K key) {
    int hash = genericHash(key); // Get the bucketIndex using genericHash
    return prime - (hash % prime); // Secondary calculation
    }
```
---

## Time Complexity 

|  Operation    |  Time Complexity     |  
|   ---         | ---                  | 
| Insertion     | O(1)                 | 
| Deletion      | O(1)                 |
| Search        | O(1)                 |

> Note: Although in the best case and average case, the operations will perform in constant time but if there are collisions and in worst case, it becomes O(n).