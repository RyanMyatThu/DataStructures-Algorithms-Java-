package Language.Java.DataStructures.HashTables.ClosedAddressing.SeparateChaining;

public class HashTableDemo {
    public static void main(String[] args) {
        // Create a new hash table instance
        HashTable<String, String> hashTable = new HashTable<>();

        // Demonstrating adding entries
        
        System.out.println("Adding entries to the hash table...");
        hashTable.add("key1", "value1");
        hashTable.add("key2", "value2");
        hashTable.add("key3", "value3");
        hashTable.add("Hello", "VEL");
        
        // Display current size
        System.out.println("Current size: " + hashTable.getSize()); // Should be 3

        // Demonstrating retrieval of entries
        System.out.println("\nRetrieving entries...");
        System.out.println("Value for key1: " + hashTable.get("key1")); // Should return "value1"
        System.out.println("Value for key2: " + hashTable.get("key2")); // Should return "value2"
        System.out.println("Value for key3: " + hashTable.get("key3")); // Should return "value3"

        // Demonstrating updating an entry
        System.out.println("\nUpdating value for key1...");
        hashTable.add("key1", "newValue1"); // Update existing key
        System.out.println("Updated value for key1: " + hashTable.get("key1")); // Should return "newValue1"

        // Demonstrating removal of an entry
        System.out.println("\nRemoving entry for key2...");
        hashTable.remove("key2");
        System.out.println("Value for key2 after removal: " + hashTable.get("key2")); // Should return null

        // Display current size after removal
        System.out.println("\nCurrent size after removal: " + hashTable.getSize()); // Should be 2

        // Demonstrating the behavior of the hash table with chaining
        System.out.println("\nAdding more entries to trigger resizing...");
        hashTable.add("key4", "value4");
        hashTable.add("key5", "value5");
        hashTable.add("key6", "value6");
        hashTable.add("key7", "value7");
        hashTable.add("key8", "value8");
        hashTable.add("key9", "value9"); // This may cause rehashing depending on load factor
        System.out.println("Current size after adding more entries: " + hashTable.getSize()); // Should reflect new size

        // Attempting to retrieve a non-existent key
        System.out.println("\nAttempting to retrieve a non-existent key:");
        System.out.println("Value for nonExistentKey: " + hashTable.get("nonExistentKey")); // Should return null

        // Check if the hash table is empty
        System.out.println("\nIs the hash table empty? " + hashTable.isEmpty()); // Should return false

        // Removing all keys to check if it becomes empty
        System.out.println("\nRemoving all entries...");
        hashTable.remove("key1");
        hashTable.remove("key3");
        hashTable.remove("key4");
        hashTable.remove("key5");
        hashTable.remove("key6");
        hashTable.remove("key7");
        hashTable.remove("key8");
        hashTable.remove("key9");
        
        // Final size check
        System.out.println("\nFinal size after removing all entries: " + hashTable.getSize()); // Should be 0
        System.out.println("Is the hash table empty now? " + hashTable.isEmpty()); // Should return true

        //Next we'll see more test cases where keys are integers and values are strings
        System.out.println();

        HashTable<Integer, String> hashTable2 = new HashTable<>();
        System.out.println("=== Test Case 1: Adding elements ===");
        hashTable2.add(1, "One");
        hashTable2.add(2, "Two");
        hashTable2.add(3, "Three");
        hashTable2.add(4, "Four");
        hashTable2.add(5, "Five");
        System.out.println("Size after adding elements: " + hashTable2.getSize()); // Expected: 5


        // Test Case 2: Retrieving elements
        System.out.println("\n=== Test Case 2: Retrieving elements ===");
        System.out.println("Key: 1, Value: " + hashTable2.get(1)); // Expected: "One"
        System.out.println("Key: 3, Value: " + hashTable2.get(3)); // Expected: "Three"
        System.out.println("Key: 5, Value: " + hashTable2.get(5)); // Expected: "Five"
        System.out.println("Key: 6, Value: " + hashTable2.get(6)); // Expected: null (not found)

        // Test Case 3: Updating an existing element
        System.out.println("\n=== Test Case 3: Updating an existing element ===");
        hashTable2.add(2, "Updated Two");
        System.out.println("Key: 2, Value: " + hashTable2.get(2)); // Expected: "Updated Two"

        // Test Case 4: Removing elements
        System.out.println("\n=== Test Case 4: Removing elements ===");
        hashTable2.remove(4);
        System.out.println("Key: 4, Value: " + hashTable2.get(4)); // Expected: null (not found)
        System.out.println("Size after removal: " + hashTable2.getSize()); // Expected: 4

        // Test Case 5: Checking if the hash table is empty
        System.out.println("\n=== Test Case 5: Checking if the hash table is empty ===");
        System.out.println("Is the hash table empty? " + hashTable2.isEmpty()); // Expected: false

        // Test Case 6: Getting the current size
        System.out.println("\n=== Test Case 6: Getting the current size ===");
        System.out.println("Current size of hash table: " + hashTable2.getSize()); // Expected: 4

        // Test Case 7: Removing all elements
        System.out.println("\n=== Test Case 7: Removing all elements ===");
        hashTable2.remove(1);
        hashTable2.remove(2);
        hashTable2.remove(3);
        hashTable2.remove(5);
        System.out.println("Size after removing all elements: " + hashTable2.getSize()); // Expected: 0
        System.out.println("Is the hash table empty? " + hashTable2.isEmpty()); // Expected: true

        // Test Case 8: Adding duplicate keys
        System.out.println("\n=== Test Case 8: Adding duplicate keys ===");
        hashTable2.add(1, "One");
        hashTable2.add(1, "Another One");
        System.out.println("Key: 1, Value: " + hashTable2.get(1)); // Expected: "Another One"
        System.out.println("Size after adding duplicate key: " + hashTable2.getSize()); // Expected: 1

        // Test Case 9: Load factor and resizing
        System.out.println("\n=== Test Case 9: Load factor and resizing ===");
        for (int i = 6; i <= 15; i++) {
            hashTable2.add(i, "Number " + i);
        }
        System.out.println("Size after adding more elements: " + hashTable2.getSize()); // Expected: 10
        System.out.println("Load factor (size/bucket): " + (1.0 * hashTable2.getSize() / 10)); // Should exceed 0.7 to trigger resizing

        // Test Case 10: Getting values after resizing
        System.out.println("\n=== Test Case 10: Getting values after resizing ===");
        for (int i = 6; i <= 15; i++) {
            System.out.println("Key: " + i + ", Value: " + hashTable2.get(i)); // Expected: "Number i"
        }
    }
}
