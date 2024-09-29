# Doubly Linked List

---

## What Is a Doubly Linked List?
A doubly linked list is a type of linked list data structure similar to singly linked list. The only difference is that the nodes of a doubly linked list keep track of both `next` and `prev` nodes. We can also keep track of both `head` and `tail` nodes of the linked list as well.


>The **DoublyLinkedList** class we've implemented can perform *insertion*, *deletion* and *search*, maintaining an efficient list structure with constant-time insertions and deletions at the **head** and **tail**.


This document will further explore the implementation of the "Doubly Linked List" data structure along with the uses of its methods.

---

## Class Structure

#### The Node Class
This class is the building block of the doubly linked list, as the doubly linked list is composed of *Nodes*.


**Class Implementation :**

```Java
public class Node{
    int data;
    Node next;
    Node prev;
    Node(int data){
        this.data = data;
    }
}
```

As shown above, this particular `Node` class will keep track of both its previous and next nodes. The value of the `Node` will be stored in `data`.


#### The DoublyLinkedList Class
As the name suggests, this is indeed the main class of the data structure and the all of the methods, that deal with *insertion*, *deletion*, and *searching*  will be implemented in this class.


```Java
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
    public int search(int val){
        Node cur = head;
        int count = 1;
        while(cur != null){
            if(val == cur.data){
                return count;
            }
            cur = cur.next;
            count++;
        }
        return -1;
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
```

---

## Insertion Methods

1. `insertFirst(int val)`

The first insertion method is `insertFirst(int val)`. This method will create a new node with the given value and then insert it in the **front** of the linked list. Of course, there are some edge cases such as *inserting the new node in an empty list*, but this implementation deals with this certain edge case. [^1]

**Code Preview :**
```Java
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
```
**Examples :**

```Java
// Test Case 1: Insert elements at the beginning
        list.insertFirst(1);
        list.insertFirst(2);
        list.insertFirst(3);
        list.display();  // 3 --> 2 --> 1 --> null
        System.out.println(list.getSize());
```

---

2. `insertLast(int val)`

The next insertion method is called `insertLast(int val)`. It will create a new node with the given value and then insert it in the **back** of the linked list. This implementation deals with the edge cases such as inserting a node into an empty linked list by assigning both `head` and `tail` as the new node.


**Code Preview :**

```Java
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
```

**Examples :**

```Java
 // Test Case 2: Insert elements at the end
        list.insertLast(4);
        list.insertLast(5);
        list.display();  // 3 --> 2 --> 1 --> 4 --> 5 --> null
        System.out.println(list.getSize());
```

3. **`insertAt(int index, int val)`**

Moving on, we'll look into another insertion method called `insertAt(int index, int val)`. This method will create a new node with the given value (`val`). Then it'll traverse through the linked list until it reaches the element **at the desired index** [^2]. Next, we'll insert the newly created node. It also handles edge cases by comparing the size of the linked list with the index. 

**Code Preview :**

```Java
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
        // Here's how adding a new node works while using prev and next
        current.prev.next = newNode;
        newNode.prev = current.prev;
        newNode.next = current;
        current.prev = newNode;
        size++;

    }
```

**Examples :**

```Java
// Test Case 3: Insert at specific index
        list.insertAt(3, 10);  // Insert at index 3
        list.display();  // 3 --> 2 --> 10 --> 1 --> 4 --> 5 --> null
        System.out.println(list.getSize());
```

---

## Removal Methods

4. `removeFirst()`

This particular method will be the opposite of `insertFirst(...)` method. This will instead remove the current head of the linked list and assign the new head to the node after the current head. If the linked list has only one node, then its gonna make both `head` and `tail` `null`.

**Code Preview :**

```Java
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
```

**Example :**
```Java
// Test Case 4: Remove the first element
        list.removeFirst();
        list.display();  // 2 --> 10 --> 1 --> 4 --> 5 --> null
        System.out.println(list.getSize());
```

5. `removeLast()`

As the name suggests, this method will remove the last node of the linked list. Since, the `tail` keeps track of the current last node, it's going to be constant time. If the `size` is 1, both `head` and `tail` will be `null`.

**Code Preview :**
```Java
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
```

**Example :**
```Java
// Test Case 5: Remove the last element
        list.removeLast();
        list.display();  // 2 --> 10 --> 1 --> 4 --> null
        System.out.println(list.getSize());
```

6. `removeAt(int index)`

The method called `removeAt(int index)` will traverse the linked list until it reaches the `desired index`. Once the index is reached, we shall delete the node pointed by `current`. 

**Code Preview :**
```Java
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
```

**Example :**
```Java
// Test Case 6: Remove at specific index
        list.removeAt(2);  // Remove element at index 2
        list.display();  // 2 --> 1 --> 4 --> null
        System.out.println(list.getSize());
```
---

## Searching Method

7. `search(int val)`

This method traverses the linked list until it reaches the desired node with the matching value. If the node isn't found it returns `-1`. Otherwise, it returns the index of the node.

**Code Preview :**

```Java
 public int search(int val){
        Node cur = head;
        int count = 1;
        while(cur != null){
            if(val == cur.data){
                return count;
            }
            cur = cur.next;
            count++;
        }
        return -1;
    }
```

**Example :**

```Java
 // Test Case 7: Search for an element
       System.out.println(list.search(1)); // 2
```

---

### Display Method

6. `display()`

Displays all the elements of the linked list in a readable format.

**Code Preview :**

```Java
public void display(){
        Node current = head;
        while(current != null){
            System.out.print(current.data + " --> ");
            current = current.next;
        }
        System.out.print("null");
        System.out.println();
    }
```

**Examples :**

```Java
        list.insertFirst(1);
        list.insertFirst(2);
        list.insertFirst(3);
        list.display();  // 3 --> 2 --> 1 --> null
```
---

### IsEmpty Method
7. `isEmpty()`

Check if the linked list is empty or not.

**Code Preview**
```Java
public boolean isEmpty(){
        return size == 0;
    }
```
**Example**:
```Java
DoublyLinkedList list = new DoublyLinkedList();
list.isEmpty() // true
```
---

### Time Complexity Overview

| Operation  | Time Complexity   | 
|---         |---                |             
|  Insertion at First |   O(1)       |
| Insertion at End    |   O(1)       |
| Insert at Index     |   O(n)       |
| Delete at Index     |   O(n)       |
| Delete at End       |   O(1)       |
| Delete at First     |   O(1)       |
| Display             |   O(n)       |
| Search For Element  |   O(n)       |



>This implementation covers basic functionalities, and it can be extended to include more advanced operations such as reverse, detecting cycles, etc.


[^1]: It handles the edge case by making the new node both the `head` and `tail` of the linked list.

[^2]: Unlike singly linked lists, we could add a new node without having to stop traversing **just before the desired index**. It is all due to having `prev` and `next` attributes of the `Node`.

