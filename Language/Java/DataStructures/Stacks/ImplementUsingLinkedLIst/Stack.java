package Language.Java.DataStructures.Stacks.ImplementUsingLinkedLIst;

public class Stack {
    Node top;
    int size;
    
    public void push(int data){
        Node newNode = new Node(data);
        if(top == null){
            top = newNode;
            size++;
        } else {
            newNode.next = top;
            top = newNode;
            size++;
        }
    }

    public int peek(){
        if(isEmpty()) throw new RuntimeException("Empty stack");
        return top.val;

    }
    public boolean isEmpty(){
        return top == null;
    }
    public int pop(){
        if(isEmpty()) throw new RuntimeException("Empty stack");
        int res = top.val;
        top = top.next;
        size--;
        return res;
    }
    public void display(){
        Node current = top;
        while(current != null){
            System.out.print(current.val+ " ");
            current = current.next;
        }
        System.out.println();
    }
}
