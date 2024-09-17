package Language.Java.Algorithms.SortingAlgorithms.InsertionSort;

import java.util.Arrays;

public class InsertionSort {
    public static void sort(int[] arr) {
       for (int i = 1; i < arr.length; i++) {
        int current = arr[i];
        int j = i -1;
        
        // Shift elements to the right until the correct position for 'current' is found
        while(j >= 0 && arr[j] > current){
            arr[j+1] = arr[j];
            j--;
        }
        arr[j+1] = current; // The correct position for 'current'
       }
    }
    
    public static void main(String[] args) {
        int[] inputArray = new int[] {5,3,1,8,9,10,10}; // Testing our input Array
        sort(inputArray);
        System.out.println(Arrays.toString(inputArray)); // [1, 3, 5, 8, 9, 10, 10]
    }

}
