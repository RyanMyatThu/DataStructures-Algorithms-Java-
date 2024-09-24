package Language.Java.DataStructures.Stacks.ImplementUsingArrays;

public class Stack {
    int top = -1;
    int[] arr;
    Stack(int size){
        arr = new int[size];
    }
    public void push(int val){
        top++;
        arr[top] = val;
    }
    public int pop(){
        if(top == -1) throw new RuntimeException("Empty stack");
        int res = arr[top];
        top--;
        return res;
        
    }
    public int peek(){
        return arr[top];
    }
    public boolean isEmpty(){
        return top == -1;
    }
    public void display(){

        for (int i = top; i >= 0; i--) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    public boolean isFull(){
        return top == arr.length - 1;
    }
}
