package Language.Java.DataStructures.LinkedLists.SinglyLinkedLists;

public class SinglyLinkedListDemo {
    
    public static void main(String[] args) {
        // Test case 1: Insert at the beginning of the list
        SinglyLinkedList list = new SinglyLinkedList();
        list.insertFirst(10);
        list.insertFirst(20);
        list.insertFirst(30);
        list.display();  // Expected Output: 30 --> 20 --> 10 --> null

        // Test case 2: Insert at the end of the list
        list.insertEnd(40);
        list.insertEnd(50);
        list.display();  // Expected Output: 30 --> 20 --> 10 --> 40 --> 50 --> null

        // Test case 3: Insert at a specific index
        list.insertAt(3, 25); 
        list.display();  // Expected Output: 30 --> 20 --> 25 --> 10 --> 40 --> 50 --> null

        // Test case 4: Delete a node at the beginning
        list.deleteNode(1);
        list.display();  // Expected Output: 20 --> 25 --> 10 --> 40 --> 50 --> null

        // Test case 5: Delete a node at the end
        list.deleteNode(5); 
        list.display();  // Expected Output: 20 --> 25 --> 10 --> 40 --> null

        // Test case 6: Delete a node in the middle
        list.deleteNode(3);
        list.display();  // Expected Output: 20 --> 25 --> 40 --> null

        // Test case 7: Search for a node that exists
        int searchResult = list.search(25);
        System.out.println("Index of 25: " + searchResult);  // Expected Output: Index of 25: 2

        // Test case 8: Search for a node that doesn't exist
        searchResult = list.search(100);
        System.out.println("Index of 100: " + searchResult);  // Expected Output: Index of 100: -1

        // Test case 9: Delete node with only one element left
        SinglyLinkedList singleElementList = new SinglyLinkedList();
        singleElementList.insertFirst(99);
        singleElementList.display();  // Expected Output: 99 --> null
        singleElementList.deleteNode(1);
        singleElementList.display();  // Expected Output: null

        // Test case 10: Delete from an empty list (Should throw an exception)
        try {
            singleElementList.deleteNode(1);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());  // Expected Output: Out of bounds for size 0
        }

        // Test case 11: Insert into an empty list
        SinglyLinkedList emptyList = new SinglyLinkedList();
        emptyList.insertEnd(5);
        emptyList.display();  // Expected Output: 5 --> null
    }

}
