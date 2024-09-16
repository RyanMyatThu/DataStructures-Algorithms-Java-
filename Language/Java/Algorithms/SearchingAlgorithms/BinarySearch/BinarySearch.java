/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

 package Language.Java.Algorithms.SearchingAlgorithms.BinarySearch;
/**
 *
 * @author ryanw
 */
public class BinarySearch {
    public static int search(int[] arr, int target){
        int left = 0;
        int right = arr.length - 1;
        
        while(left <= right){
            int mid = Math.floorDiv(left+right, 2);
            if(arr[mid] == target){
                return mid;
            } 
            if(arr[mid] < right){
                right = mid - 1;
            }
            if(arr[mid] > right){
                left = mid + 1;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        
    }

    
}
