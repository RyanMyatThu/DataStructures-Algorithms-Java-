package Language.Java.DataStructures.LinkedLists.SinglyLinkedLists;

public class SinglyLinkedList {
    private Node head;
    private int size = 0;
    SinglyLinkedList(){
        this.head = null;
    }

    public void insertFirst(int val){
        Node newNode = new Node(val);
        if(isEmpty()) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    public void insertEnd(int val){
        Node newNode = new Node(val);
        if(isEmpty()){
            head = newNode;
        } else {
            Node current = head;
            while(current.next != null){
                current = current.next;
            }
            current.next = newNode;
            
        }
        size++;
    }

    public void insertAt(int index, int value){
        if(size + 1 < index) throw new RuntimeException("Out of bounds for size " + size);
        if(index == 1){
           insertFirst(value);
        } else {
        Node newNode = new Node(value);
        int count = 1;
        Node current = head;
        while(count < index - 1){
            current = current.next;
            count++;
        }

        newNode.next = current.next;
        current.next = newNode;

        size++;
    }

    }
    public void deleteNode(int index){
        if(index > size || index < 1) throw new RuntimeException("Out of bounds for size " + size);
        if(index == 1){
            Node newHead = head.next;
            head = newHead;
            size--;
            return ;
        } 
        int count = 1;
        Node current = head;
        Node prev = null;
        while(count < index){
            prev = current;
            current = current.next;
            count++;
        }
        if(prev != null){
        prev.next = current.next;
        }
        size--;
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

    public int search(int target){
        Node current = head;
        int count = 1;
        while(current != null){
            if(current.data == target){
                return count;
            }
            current = current.next;
            count++;
        }
        return -1;
    }
    

}
