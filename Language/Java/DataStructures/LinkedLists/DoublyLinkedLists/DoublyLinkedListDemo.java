package Language.Java.DataStructures.LinkedLists.DoublyLinkedLists;

public class DoublyLinkedListDemo {
    
    public static void main(String[] args) {
        DoublyLinkedList list = new DoublyLinkedList();
        
        // Test Case 1: Insert elements at the beginning
        list.insertFirst(1);
        list.insertFirst(2);
        list.insertFirst(3);
        list.display();  // 3 --> 2 --> 1 --> null
        System.out.println(list.getSize());
        
        // Test Case 2: Insert elements at the end
        list.insertLast(4);
        list.insertLast(5);
        list.display();  // 3 --> 2 --> 1 --> 4 --> 5 --> null
        System.out.println(list.getSize());

        // Test Case 3: Insert at specific index
        list.insertAt(3, 10);  // Insert at index 3
        list.display();  // 3 --> 2 --> 10 --> 1 --> 4 --> 5 --> null
        System.out.println(list.getSize());

        // Test Case 4: Remove the first element
        list.removeFirst();
        list.display();  // 2 --> 10 --> 1 --> 4 --> 5 --> null
        System.out.println(list.getSize());

        // Test Case 5: Remove the last element
        list.removeLast();
        list.display();  // 2 --> 10 --> 1 --> 4 --> null
        System.out.println(list.getSize());

        // Test Case 6: Remove at specific index
        list.removeAt(2);  // Remove element at index 2
        list.display();  // 2 --> 1 --> 4 --> null
        System.out.println(list.getSize());

        // Test Case 7: Search for an element
       System.out.println(list.search(1)); // 2


        // Test Case 8: Remove the only element
        list.removeFirst();
        list.removeFirst();
        list.removeFirst();  // List is now empty
        list.display();  // null
        System.out.println(list.getSize());


    }
}
