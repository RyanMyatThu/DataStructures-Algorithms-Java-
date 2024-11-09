package Language.Java.Algorithms.SortingAlgorithms.BubbleSort;

import java.util.Arrays;

public class BubbleSort {
    static void sort(int[] arr){
      boolean swapped;
      for(int i = 0; i < arr.length; i++){
        swapped = false;
      for(int k = 0; k < arr.length - i - 1; k++){
        if(arr[k] > arr[k+1]){
            int temp = arr[k+1];
            arr[k+1] = arr[k];
            arr[k] = temp;
            swapped = true;
        }
      }
      if(!swapped){
        break;
      }
    }
    }
   public static void main(String[] args) {
    int[] array = new int[] {4,3,2,5,1,9,1,412,3,29,5};
    sort(array);
    System.out.println(Arrays.toString(array));
   }
    
}
