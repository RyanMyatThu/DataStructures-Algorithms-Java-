package Language.Java.DataStructures.Stacks.ImplementUsingLinkedLIst;

public class StackDemo {
    public static void main(String[] args) {
        Stack stack = new Stack(); // Create a stack of size 5

        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.display();  // Expected output: 30 20 10

        System.out.println("Peek: " + stack.peek());  // Expected output: Peek: 30
        System.out.println("Popped: " + stack.pop()); // Expected output: Popped: 30

        stack.display();  // Expected output: 20 10
    }
}
