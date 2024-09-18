package Language.Java.Algorithms.SortingAlgorithms.QuickSort;

import java.util.Arrays;

public class QuickSort {
    private static int partition(int[] arr, int left, int right){
        int pivot = arr[right]; //Pivot will be the last element
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if(arr[j] <= pivot){   //Swap if arr[j] is less than or equal to pivot.
               i++;
               int temp = arr[i];
               arr[i] = arr[j];
               arr[j] = temp;   
            }
        }
        int temp = arr[right];    // After loop, we'll put the pivot element to its correct position. Which is arr[i + 1]
        arr[right] = arr[i+ 1];
        arr[i + 1] = temp;
        return i + 1;                 

    }
    public static void quickSort(int[] arr, int left, int right){
        if(left < right){
            int pivot = partition(arr, left, right);
            quickSort(arr, left, pivot - 1);
            quickSort(arr, pivot + 1, right);
        }
    }
    public static void main(String[] args) {
        int[] arr = new int[] {2,4,3,5,1};
        quickSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
        
    }
}
