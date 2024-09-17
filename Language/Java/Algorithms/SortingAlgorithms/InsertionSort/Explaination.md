# **Insertion Sort Algorithm**
---
## *What Is Insertion Sort?*
**Insertion Sort** is a simple and intuitive sorting algorithm. It works similarly to how you might sort playing cards in your hands. The list is divided into a "sorted" and "unsorted" section. Values from the unsorted section are picked and inserted into the correct position in the sorted section.
>The algorithm takes one value at a time from the unsorted part of the array and puts it into the right place in the sorted part of the array, until the array is sorted.
---

### **How Does It Work?**
- Start from the second element of the array `arr[1]` and consider the first element `arr[0]` as the sorted part.
- Compare the current element to its predecessor(s).
- Shift the larger elements in the sorted section to the right to make room for the current element.
- Once the correct position is reached we'll insert the current element there.
- Repeat for all elements until the array is fully sorted.

---
#### **Code preview**

``` Java
  public static void sort(int[] arr) {
       for (int i = 1; i < arr.length; i++) {
        int current = arr[i]; //Start from the second element of the array
        int j = i -1;
        
        // Shift elements to the right until the correct position for 'current' is found
        while(j >= 0 && arr[j] > current){
            arr[j+1] = arr[j];
            j--;
        }
        arr[j+1] = current; // The correct position for 'current'
       }
    }
```
## Time Complexity
- **Best Case**: O(n) – `When the array is already sorted.`
- **Worst Case**: O(n²) – `When the array is sorted in reverse order.`
- **Average Case**: O(n²).

## Space Complexity
- **Space Complexity**: O(1) – Insertion Sort is an in-place sorting algorithm.