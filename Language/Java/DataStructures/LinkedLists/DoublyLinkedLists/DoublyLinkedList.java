package Language.Java.DataStructures.LinkedLists.DoublyLinkedLists;

public class DoublyLinkedList {
    private Node head;
    private Node tail;
    private int size = 0;

    public int getSize(){
        return size;
    }

    public void insertFirst(int val){
        Node newNode = new Node(val);
        if(isEmpty()){
            head = tail = newNode; 
        } else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        size++;
    }
    public void insertLast(int val){
        if(isEmpty()){
            insertFirst(val);
        } else {
            Node newNode = new Node(val);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            size++;
        }
        
    }
    public void insertAt(int index, int val){
        if(index > size + 1) throw new RuntimeException("Index out of bounds for size " + size);
        if(index == 1){
            insertFirst(val);
            return;
        }
        if(index == size + 1){
            insertLast(val);
            return;
        }
        Node current = head;
        int count = 1;
        while(count < index){
            current = current.next;
            count++;
        }
        Node newNode = new Node(val);
        current.prev.next = newNode;
        newNode.prev = current.prev;
        newNode.next = current;
        current.prev = newNode;
        size++;

    }

    public void removeFirst(){
        if(isEmpty()) throw new RuntimeException("List is empty");
        if(size == 1){
            head = null;
            tail = null;
        } else {
        head = head.next;
        }
        size--;
    }
    public void removeLast(){
        if(isEmpty()) throw new RuntimeException("List is empty");
        if(size == 1){
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
    }
    public void removeAt(int index){
        if(isEmpty() || index > size) throw new RuntimeException("List is empty or out of bounds");
        
        if(index == 1){
            removeFirst();
            return;
        } 
        if(index == size){
            removeLast();
            return;
        }

        int count = 1;
        Node current = head;
        while(count < index){
            current = current.next;
            count++;
        }
        Node prev = current.prev;
        Node next = current.next;
        next.prev = prev;
        prev.next = next;
        size --;
    }
    public void display(){
        Node current = head;
        while(current != null){
            System.out.print(current.data + " --> ");
            current = current.next;
        }
        System.out.print("null");
        System.out.println();
    }

    public boolean isEmpty(){
        return size == 0;
    }
    
}
