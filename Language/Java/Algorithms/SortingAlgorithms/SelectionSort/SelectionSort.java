package Language.Java.Algorithms.SortingAlgorithms.SelectionSort;

import java.util.Arrays;

public class SelectionSort {
    static void sort(int[] arr){
       for (int i = 0; i < arr.length; i++) {
           int min = arr[i];
           int minIndex = i;
           for( int j = i + 1; j < arr.length; j++){
                    if(arr[j] < min){
                        min = arr[j];
                        minIndex = j;
                    }
           }
           if(arr[i] > min){
            int temp = arr[i];
            arr[i] = min;
            arr[minIndex] = temp;
           } 
       }
    }
    public static void main(String[] args) {
        int[] array = new int[] {38, 27, 43, 3, 9, 82, 10};
        sort(array);
        System.out.println(Arrays.toString(array));
    }
}
