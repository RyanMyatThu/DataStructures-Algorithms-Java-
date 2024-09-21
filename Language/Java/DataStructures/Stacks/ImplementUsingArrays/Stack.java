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
    public void peek(){
        System.out.println(arr[top]);
    }
}
