package Language.Java.Algorithms.SortingAlgorithms.MergeSort;

import java.util.Arrays;

public class MergeSort {
    public static void mergeSort(int[] arr){
        if(arr.length <= 1) return;
        int left = 0;
        int right = arr.length - 1;
        int mid = (left + right) / 2;
        int i = 0;
        int j = 0;
        int[] leftSubArr = new int[mid + 1];
        int[] rightSubArr = new int[arr.length - leftSubArr.length];

        for(; i < arr.length; i++){
            if( i <= mid){
                leftSubArr[i] = arr[i];
            } else {
                rightSubArr[j] = arr[i];
                j++;
            }
        }
        
        //Recursive calls to left and right sub arrays.
        mergeSort(leftSubArr);
        mergeSort(rightSubArr);
        merge(leftSubArr, rightSubArr, arr);
    }

    private static void merge(int[] leftArr, int[] rightArr, int[] arr ){
        int leftLength = leftArr.length;
        int rightLength = rightArr.length;
        int i = 0;
        int j = 0;
        int z = 0;
        while(i < leftLength && j < rightLength){
            if(leftArr[i] > rightArr[j]){
                arr[z] = rightArr[j];
                j++;
            } else {
                arr[z] = leftArr[i];
                i++;
            }
            z++;
        }
        while(i < leftLength){
            arr[z] = leftArr[i];
            i++;
            z++;
        }
        while(j < rightLength){
            arr[z] = rightArr[j];
            j++;
            z++;
        
        }
        
    }

    public static void main(String[] args) {
        int[] array ={38, 27, 43, 3, 9, 82, 10};
        mergeSort(array);
        System.out.println(Arrays.toString(array));
    }
    
}
