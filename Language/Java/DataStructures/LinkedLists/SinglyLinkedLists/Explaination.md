# Singly Linked Lists
---
**What is a Singly Linked List?** 
A singly linked list is a linear data structure where each **Node** contains two properties called `data` and `next`.
- **Data** is the actual value of the node.
- **Next** is a reference pointer to the upcoming node. (A newly created node has a default value of `next` as `null`)

>The **SinglyLinkedList** class we've implemented can perform *insertion*, *deletion* and *search*, maintaining an efficient list structure with constant-time insertions and deletions at the **head**. 

This document will further explore the implementation of the "Singly Linked List" data structure along with the uses of its methods.

---

## Class Structure

#### The Node Class
This class is the building block of the singly linked list, as the singly linked list is composed of *Nodes*.

**Class Implementation**
```Java 

public class Node{
    int data;
    Node next;
    Node(int data){
        this.data = data;
    }
}
```
As the code suggests, the class holds two properties. We'll use the `data` property to store the value of the node and `next` property to store the reference to the upcoming node after this one. [^1]

#### The SinglyLinkedList Class
As the name suggests, this is indeed the main class of the data structure and the all of the methods, that deal with *insertion*, *deletion*, and *searching*  will be implemented in this class.

```Java
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
```
### Insertion methods

1. **`insertFirst(int val)`**

So, the first insertion method is `insertFirst(int val)`. This method will create a new node with the given value and then insert it in the **front** of the linked list. Of course, there are some edge cases such as *inserting the new node in an empty list*, but this implementation deals with this certain edge case.

**Code preview**
```Java
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
```

**Example** :
```Java
// Test case 1: Insert at the beginning of the list
        SinglyLinkedList list = new SinglyLinkedList();
        list.insertFirst(10);
        list.insertFirst(20);
        list.insertFirst(30);
        list.display();  // Expected Output: 30 --> 20 --> 10 --> null
```
2. **`insertAt(int index, int val)`**

Moving on, we'll look into another insertion method called `insertAt(int index, int val)`. This method will create a new node with the given value (`val`). Then it'll traverse through the linked list until it reaches the element **just before the desired index** [^2]. Next, we'll insert the newly created node. It also handles edge cases by comparing the size of the linked list with the index. [^3]

**Code Preview**
```Java
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
```
**Example**:
```Java
list.insertAt(1, 20);
list.insertAt(2, 25);
list.insertAt(3, 10);
list.insertAt(4, 40);
list.display(); // Expected Output: 20 --> 25 --> 10 --> 40 --> null
```
3. **`insertEnd(int val)`**

The third insertion method is called `insertEnd(int val)`. Just like the first insertion method, this one, adds the newly created node in the end of the linked list. It handles the edge case just like the `insertFirst(int val)` method.

**Code Preview**
```Java
public void insertEnd(int val){
    Node newNode = new Node(val);
    if (isEmpty()) {
        head = newNode;
    } else {
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }
    size++;
}
```
**Example**:
```Java
list.insertEnd(1); // 1 --> null
list.insertEnd(2); // 1 --> 2 --> null
list.insertEnd(3); // 1 --> 2 --> 3 --> null
list.display(); // Expected Output: 1 --> 2 --> 3 --> null
```
---
### Delete Method

The method called `deleteNode(int index)` will traverse the linked list until it reaches the `desired index`. [^4] Once the index is reached, we shall delete the node pointed by `current`. 

**Code Preview**
```Java
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
        //The loop ends exactly at the desired index, where current is the node to be deleted.
        if(prev != null){
        prev.next = current.next; // Deletes the node
        }
        size--; //Decrementing the size
    }
```
**Example**:
```Java
list.insertEnd(1); // 1 --> null
list.insertEnd(2); // 1 --> 2 --> null
list.insertEnd(3); // 1 --> 2 --> 3 --> null
list.display(); // Expected Output: 1 --> 2 --> 3 --> null
list.deleteNode(3);
list.display(); // Expected Output: 1 --> 2 --> null
```
---

### The Search Method
This method deals with the searching for the `target` value inside the linked list. It simply traverses the linked list looking for the `target`, if it is found, the method returns the index of the desired node. Otherwise, it returns `-1`.

**Code Preview**
```Java
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
```
**Example**:
```Java
list.insertAt(1, 20);
list.insertAt(2, 25);
list.insertAt(3, 10);
list.insertAt(4, 40);
list.display(); // Expected Output: 20 --> 25 --> 10 --> 40 --> null
list.search(10); // returns 3
list.search(20); // returns 1
```
---

### Display Method
Displays all the elements of the linked list in a readable format.

**Code Preview**
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
**Example**:
```Java
list.insertAt(1, 20);
list.insertAt(2, 25);
list.insertAt(3, 10);
list.insertAt(4, 40);
list.display(); // Expected Output: 20 --> 25 --> 10 --> 40 --> null
```
---

### IsEmpty Method
Check if the linked list is empty or not.

**Code Preview**
```Java
public boolean isEmpty(){
        return size == 0;
    }
```
**Example**:
```Java
SinglyLinkedList list = new SinglyLinkedList();
list.isEmpty() // true
```
---

### Time Complexity Overview

| Operation  | Time Complexity   | 
|---         |---                |             
|  Insertion at First |   O(1)       |
| Insertion at End    |   O(n)       |
| Insert at Index     |   O(n)       |
| Delete at Index     |   O(n)       |
| Display             |   O(n)       |
| Search For Element  |   O(n)       |



>This implementation covers basic functionalities, and it can be extended to include more advanced operations such as reverse, detecting cycles, etc.

[^1]: Keep in mind that `SinglyLinkedList` is linear and it is an unidirectional linked list. You can only traverse it in **One** direction.
[^2]: For example, the desired index is `3`, so the traversal would end at the index `2`.
[^3]: For example, we have a linked list with the size of `2` and we called the `insertAt(4, 1);` method. Since a the nodes have to be linked, we can't skip over the indexes, even though the size isn't limited like `Arrays`.
[^4]: During this process, the traversal will be different since it uses two pointers, `prev` and `current`. As the names suggest, `prev` will point the node behind `current`.