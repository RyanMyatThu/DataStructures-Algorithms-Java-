package Language.Java.DataStructures.Heaps.MaxHeap;
import java.util.Arrays;

public class Heap {
    private int capacity = 10;
    private int size = 0;
    int[] items = new int[capacity];

    private int getLeftChildIndex(int parentIndex) {
        if(!hasLeftChild(parentIndex)) throw new IllegalArgumentException();
        return (parentIndex * 2) + 1;
    }
    private int getRightChildIndex(int parentIndex){
        if(!hasRightChild(parentIndex)) throw new IllegalArgumentException();
        return (parentIndex * 2) + 2;
    }
    private int getParentIndex(int childIndex){
        if(!hasParent(childIndex)) throw new IllegalArgumentException();
        return Math.floorDiv((childIndex - 1), 2);
    }

    private boolean hasLeftChild(int parentIndex){
        return (parentIndex * 2) + 1 < size;
    }
    private boolean hasRightChild(int parentIndex){
        return (parentIndex * 2) + 2 < size;
    }
    private boolean hasParent(int childIndex){
        return childIndex > 0;
    }
    private void swap(int indexOne, int indexTwo){
        int temp = items[indexOne];
        items[indexOne] = items[indexTwo];
        items[indexTwo] = temp;
    }

    public int getSize(){
        return size;
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
        if(size == 0) throw new IllegalArgumentException();
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
    

    private void heapifyDown(){
        int index = 0;
        while(hasLeftChild(index)){
            int greaterChildIndex = getLeftChildIndex(index);
            if(hasRightChild(index) && items[getRightChildIndex(index)] > items[greaterChildIndex]){
                greaterChildIndex = getRightChildIndex(index);
            }
            if(items[greaterChildIndex] < items[index]){
                break;
            } else {
                swap(greaterChildIndex, index);
                
            }
            index = greaterChildIndex;
        }
    }

    private void heapifyUp(){
        int index = size - 1;
        while(hasParent(index) && items[getParentIndex(index)] < items[index]){
            swap(getParentIndex(index), index);
            index = getParentIndex(index);
        }

    }

   
    
}
