package Language.Java.DataStructures.Heaps.MinHeap;

import java.util.Arrays;

public class Heap{
    private int capacity = 10;
    private int size = 0;
    int[] items = new int[capacity];

    public int getSize() { return size; }
    
    private int getParentIndex(int childIndex) { return Math.floorDiv((childIndex - 1), 2); }
    private int getLeftChildIndex(int parentIndex) { return (parentIndex * 2) + 1;}
    private int getRightChildIndex(int parentIndex) { return (parentIndex *2) + 2;}

    private boolean hasLeftChild(int parentIndex) { return getLeftChildIndex(parentIndex) < size;}
    private boolean hasRightChild(int parentIndex) { return getRightChildIndex(parentIndex) < size;}
    private boolean hasParent(int childIndex) { return (getParentIndex(childIndex) >= 0);}

    private int parent(int childIndex) {
        if (!hasParent(childIndex)) throw new IndexOutOfBoundsException("No parent for index: " + childIndex);
        return items[getParentIndex(childIndex)];
    }
    private int leftChild(int parentIndex) {
        if (!hasLeftChild(parentIndex)) throw new IndexOutOfBoundsException("No left child for index: " + parentIndex);
        return items[getLeftChildIndex(parentIndex)];
    }
    private int rightChild(int parentIndex) {
        if (!hasRightChild(parentIndex)) throw new IndexOutOfBoundsException("No right child for index: " + parentIndex);
        return items[getRightChildIndex(parentIndex)];
    }

    private void swap(int indexOne, int indexTwo){
        int temp = items[indexOne];
        items[indexOne] = items[indexTwo];
        items[indexTwo] = temp;
    }

    private void ensureExtraSpace(){
        if(size == capacity){
            items = Arrays.copyOf(items, capacity * 2);
            capacity *= 2;
        }
    }

    public int peek(){
        if(size == 0) throw new IllegalArgumentException();
        return items[0];
    }

    public int poll(){
        if(size == 0)  throw new IllegalArgumentException();
        int item = items[0];
        items[0] = items[size - 1];
        size--;
        heapifyDown();
        return item;
    }

    public void add(int item){
        ensureExtraSpace();
        items[size] = item;
        size++;
        heapifyUp();
    }

    public void heapifyUp(){
        int index = size - 1;
        while(hasParent(index) && parent(index) > items[index]){
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }

    }

    public void heapifyDown(){
        int index = 0;
        while(hasLeftChild(index)){
            int smallerChildIndex = getLeftChildIndex(index);
            if(hasRightChild(index) && leftChild(index) > rightChild(index)){
                smallerChildIndex = getRightChildIndex(index);
            }
            if(items[smallerChildIndex] > items[index]){
                break;
            } else {
                swap(index, smallerChildIndex);
            }
            index = smallerChildIndex;

        }
    }

    
}