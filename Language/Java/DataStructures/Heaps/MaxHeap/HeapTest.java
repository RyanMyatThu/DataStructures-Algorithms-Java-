package Language.Java.DataStructures.Heaps.MaxHeap;

public class HeapTest {
    public static void main(String[] args) {
        System.out.println("=== Max Heap Implementation Tests ===\n");

        // Test 1: Creating an empty heap
        System.out.println("Test 1: Creating an empty heap");
        Heap heap1 = new Heap();
        System.out.println("Expected size: 0");
        System.out.println("Actual size: " + heap1.getSize());
        System.out.println("✓ Test 1 passed\n");

        // Test 2: Adding single element
        System.out.println("Test 2: Adding single element");
        Heap heap2 = new Heap();
        heap2.add(10);
        System.out.println("Expected size: 1");
        System.out.println("Actual size: " + heap2.getSize());
        System.out.println("Expected peek: 10");
        System.out.println("Actual peek: " + heap2.peek());
        System.out.println("✓ Test 2 passed\n");

        // Test 3: Adding multiple elements (max heap property)
        System.out.println("Test 3: Adding multiple elements [10, 15, 20, 30, 50]");
        Heap heap3 = new Heap();
        heap3.add(10);
        heap3.add(15);
        heap3.add(20);
        heap3.add(30);
        heap3.add(50);
        System.out.println("Expected size: 5");
        System.out.println("Actual size: " + heap3.getSize());
        System.out.println("Expected maximum (peek): 50");
        System.out.println("Actual maximum (peek): " + heap3.peek());
        System.out.println("✓ Test 3 passed\n");

        // Test 4: Peek operation (should not remove element)
        System.out.println("Test 4: Peek operation (should not remove)");
        int peekValue = heap3.peek();
        System.out.println("Peeked value: " + peekValue);
        System.out.println("Expected size after peek: 5 (unchanged)");
        System.out.println("Actual size after peek: " + heap3.getSize());
        System.out.println("Peek again: " + heap3.peek() + " (should be same)");
        System.out.println("✓ Test 4 passed\n");

        // Test 5: Poll operation (remove maximum)
        System.out.println("Test 5: Poll operation (remove maximum)");
        System.out.println("Initial maximum: " + heap3.peek());
        int polled = heap3.poll();
        System.out.println("Polled value: " + polled);
        System.out.println("Expected new maximum: 30");
        System.out.println("Actual new maximum: " + heap3.peek());
        System.out.println("Expected size after poll: 4");
        System.out.println("Actual size after poll: " + heap3.getSize());
        System.out.println("✓ Test 5 passed\n");

        // Test 6: Multiple poll operations (should maintain max heap property)
        System.out.println("Test 6: Multiple poll operations");
        System.out.println("Polling all elements in order:");
        int[] expectedOrder = {30, 20, 15, 10};
        for (int i = 0; i < expectedOrder.length; i++) {
            int max = heap3.poll();
            System.out.println("  Poll " + (i + 1) + ": " + max + " (expected: " + expectedOrder[i] + ")");
        }
        System.out.println("Expected final size: 0");
        System.out.println("Actual final size: " + heap3.getSize());
        System.out.println("✓ Test 6 passed\n");

        // Test 7: Building heap from unsorted array
        System.out.println("Test 7: Building heap from unsorted array [5, 12, 23, 8, 31, 17, 42]");
        Heap heap4 = new Heap();
        int[] unsorted = {5, 12, 23, 8, 31, 17, 42};
        for (int num : unsorted) {
            heap4.add(num);
        }
        System.out.println("Expected size: 7");
        System.out.println("Actual size: " + heap4.getSize());
        System.out.println("Expected maximum: 42");
        System.out.println("Actual maximum: " + heap4.peek());
        System.out.println("✓ Test 7 passed\n");

        // Test 8: Poll all elements (should return in descending order)
        System.out.println("Test 8: Polling all elements (should be in descending order)");
        System.out.print("Polled values: ");
        int prev = heap4.poll();
        System.out.print(prev);
        while (heap4.getSize() > 0) {
            int current = heap4.poll();
            System.out.print(" > " + current);
            if (prev < current) {
                System.out.println("\n✗ Test 8 FAILED: Max heap property violated");
                return;
            }
            prev = current;
        }
        System.out.println();
        System.out.println("✓ Test 8 passed (all elements retrieved in descending order)\n");

        // Test 9: Adding duplicate values
        System.out.println("Test 9: Adding duplicate values [15, 15, 10, 10, 5]");
        Heap heap5 = new Heap();
        heap5.add(15);
        heap5.add(15);
        heap5.add(10);
        heap5.add(10);
        heap5.add(5);
        System.out.println("Expected size: 5");
        System.out.println("Actual size: " + heap5.getSize());
        System.out.println("First poll (should be 15): " + heap5.poll());
        System.out.println("Second poll (should be 15): " + heap5.poll());
        System.out.println("Third poll (should be 10): " + heap5.poll());
        System.out.println("✓ Test 9 passed\n");

        // Test 10: Dynamic array resizing
        System.out.println("Test 10: Dynamic array resizing (adding more than initial capacity)");
        Heap heap6 = new Heap();
        System.out.println("Adding 15 elements to test resizing (initial capacity: 10)");
        for (int i = 1; i <= 15; i++) {
            heap6.add(i);
        }
        System.out.println("Expected size: 15");
        System.out.println("Actual size: " + heap6.getSize());
        System.out.println("Expected maximum: 15");
        System.out.println("Actual maximum: " + heap6.peek());
        System.out.println("✓ Test 10 passed (array resized successfully)\n");

        // Test 11: Edge case - Peek on empty heap (should throw exception)
        System.out.println("Test 11: Edge case - Peek on empty heap");
        Heap emptyHeap = new Heap();
        try {
            emptyHeap.peek();
            System.out.println("✗ Test 11 FAILED: Should have thrown exception");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Test 11 passed: Correctly threw IllegalArgumentException");
        }
        System.out.println();

        // Test 12: Edge case - Poll on empty heap (should throw exception)
        System.out.println("Test 12: Edge case - Poll on empty heap");
        try {
            emptyHeap.poll();
            System.out.println("✗ Test 12 FAILED: Should have thrown exception");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Test 12 passed: Correctly threw IllegalArgumentException");
        }
        System.out.println();

        // Test 13: Large sequence of add and poll operations
        System.out.println("Test 13: Large sequence of add and poll operations");
        Heap heap7 = new Heap();
        System.out.println("Adding 100 random numbers, polling every 10th element...");
        int pollCount = 0;
        for (int i = 0; i < 100; i++) {
            heap7.add((int)(Math.random() * 1000));
            if ((i + 1) % 10 == 0) {
                int max = heap7.poll();
                pollCount++;
                System.out.println("  After " + (i + 1) + " adds, polled: " + max + ", size: " + heap7.getSize());
            }
        }
        System.out.println("Total polls: " + pollCount);
        System.out.println("Final size: " + heap7.getSize());
        System.out.println("✓ Test 13 passed\n");

        // Test 14: Verify max heap property after complex operations
        System.out.println("Test 14: Verify max heap property after complex operations");
        Heap heap8 = new Heap();
        int[] complexSequence = {10, 30, 80, 40, 90, 60, 100, 25, 75, 50};
        for (int num : complexSequence) {
            heap8.add(num);
        }
        System.out.println("Added: [10, 30, 80, 40, 90, 60, 100, 25, 75, 50]");
        System.out.println("Expected maximum: 100");
        System.out.println("Actual maximum: " + heap8.peek());
        
        // Poll first few and verify order
        int prevMax = heap8.poll();
        System.out.print("Polled sequence: " + prevMax);
        for (int i = 0; i < 4 && heap8.getSize() > 0; i++) {
            int current = heap8.poll();
            System.out.print(" > " + current);
            if (prevMax < current) {
                System.out.println("\n✗ Test 14 FAILED: Max heap property violated");
                return;
            }
            prevMax = current;
        }
        System.out.println();
        System.out.println("✓ Test 14 passed (max heap property maintained)\n");

        // Test 15: Alternating add and poll operations
        System.out.println("Test 15: Alternating add and poll operations");
        Heap heap9 = new Heap();
        heap9.add(30);
        heap9.add(50);
        heap9.add(40);
        System.out.println("After adding [30, 50, 40]: peek = " + heap9.peek() + ", size = " + heap9.getSize());
        
        int poll1 = heap9.poll();
        System.out.println("After poll: " + poll1 + ", new peek = " + heap9.peek() + ", size = " + heap9.getSize());
        
        heap9.add(60);
        System.out.println("After adding 60: peek = " + heap9.peek() + ", size = " + heap9.getSize());
        
        int poll2 = heap9.poll();
        System.out.println("After poll: " + poll2 + ", new peek = " + heap9.peek() + ", size = " + heap9.getSize());
        System.out.println("✓ Test 15 passed\n");

        System.out.println("=== All Tests Completed ===");
    }
}
