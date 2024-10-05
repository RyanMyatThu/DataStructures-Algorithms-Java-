package Language.Java.DataStructures.HashTables.OpenAddressing.LinearProbing;

public class HashTableDemo {
    public static void main(String[] args) {
        HashTable<String, Integer> hashTable = new HashTable<>();

        // 1. Add elements to the hash table
        System.out.println("### Adding Elements ###");
        hashTable.add("One", 1);
        hashTable.add("Two", 2);
        hashTable.add("Three", 3);
        hashTable.printHashTable();
        System.out.println("Added 'One', 'Two', 'Three'.");

        // Check size
        System.out.println("Size after adding elements: " + hashTable.size()); // Expected: 3

        // 2. Add a duplicate key to update value
        System.out.println("\n### Adding Duplicate Key to Update Value ###");
        hashTable.add("Two", 22);
        System.out.println("Updated value of 'Two'.");
        System.out.println("Get value for 'Two': " + hashTable.get("Two")); // Expected: 22
        hashTable.printHashTable();

        // 3. Add a null key/value
        System.out.println("\n### Adding Null Key/Value ###");
        hashTable.add(null, 10); // Should be ignored as null key is not allowed
        hashTable.add("Four", null); // Should be ignored as null value is not allowed
        System.out.println("Tried adding null key and null value, size should be unchanged.");
        System.out.println("Size after attempting null add: " + hashTable.size()); // Expected: 3

        // 4. Get element by key
        System.out.println("\n### Getting Elements by Key ###");
        System.out.println("Get value for 'One': " + hashTable.get("One")); // Expected: 1
        System.out.println("Get value for 'Four': " + hashTable.get("Four")); // Expected: null (not present)

        // 5. Remove element by key
        System.out.println("\n### Removing Elements ###");
        hashTable.remove("Two");
        System.out.println("Removed 'Two'.");
        System.out.println("Get value for 'Two' after removal: " + hashTable.get("Two")); // Expected: null
        System.out.println("Size after removing 'Two': " + hashTable.size()); // Expected: 2

        // 6. Remove element not present in the hash table
        System.out.println("\n### Removing Non-existent Element ###");
        hashTable.remove("Five"); // Should do nothing
        System.out.println("Tried removing non-existent key 'Five'.");
        System.out.println("Size should be unchanged: " + hashTable.size()); // Expected: 2

        // 7. Test resizing of the hash table
        System.out.println("\n### Testing Resizing ###");
        hashTable.add("Five", 5);
        hashTable.add("Six", 6);
        hashTable.add("Seven", 7);
        hashTable.add("Eight", 8);
        hashTable.add("Nine", 9);
        hashTable.add("Ten", 10);
        hashTable.add("Eleven", 11); // Should trigger resizing
        System.out.println("Added several elements to trigger resizing.");
        System.out.println("Size after resizing: " + hashTable.size()); // Expected: 9
        hashTable.printHashTable();

        // 8. Test Collision Handling
        System.out.println("\n### Testing Collision Handling ###");
        hashTable.add("Aa", 101);
        hashTable.add("BB", 202); // Intentionally adding colliding keys (Aa = 2112, BB = 2112)
        System.out.println("Added 'Aa' and 'BB' (may have the same hash).");
        System.out.println("Get value for 'Aa': " + hashTable.get("Aa")); // Expected: 101
        System.out.println("Get value for 'BB': " + hashTable.get("BB")); // Expected: 202
        hashTable.printHashTable();

        // 9. Check isEmpty() and size()
        System.out.println("\n### Checking if HashTable is Empty ###");
        System.out.println("Is the hash table empty? " + hashTable.isEmpty()); // Expected: false
        System.out.println("Current size: " + hashTable.size()); // Expected: 11

        // 10. Add an element with an integer key
        System.out.println("\n### Adding Element with Integer Key ###");
        HashTable<Integer, String> intKeyTable = new HashTable<>();
        intKeyTable.add(1, "One");
        System.out.println("Added integer key 1 with value 'One'.");
        System.out.println("Get value for key 1: " + intKeyTable.get(1)); // Expected: "One"

        // 11. Remove all elements one by one
        System.out.println("\n### Removing All Elements One by One ###");
        hashTable.remove("One");
        hashTable.remove("Three");
        hashTable.remove("Five");
        hashTable.remove("Six");
        hashTable.remove("Seven");
        hashTable.remove("Eight");
        hashTable.remove("Nine");
        hashTable.remove("Ten");
        hashTable.remove("Eleven");
        hashTable.remove("Aa");
        hashTable.remove("BB");
        System.out.println("Removed all elements.");
        System.out.println("Is hash table empty? " + hashTable.isEmpty()); // Expected: true
        System.out.println("Final size: " + hashTable.size()); // Expected: 0

        // 12. Edge Case: Removing from an empty hash table
        System.out.println("\n### Edge Case: Removing from Empty HashTable ###");
        try {
            hashTable.remove("NonExistent");
        } catch (RuntimeException e) {
            System.out.println("Caught RuntimeException: " + e.getMessage()); // Expected: "Hash table is empty"
        }
    }
}
