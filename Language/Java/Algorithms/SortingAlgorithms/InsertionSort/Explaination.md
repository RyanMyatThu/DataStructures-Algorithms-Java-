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
- O(1) – Insertion Sort is an in-place sorting algorithm.

---
### Step-By-Step process

Let’s sort the array `[5, 3, 8, 6, 2]` using Insertion Sort:
First, we'll start by selecting the second element of the array, which, in this case is `3`. The value of j is `0` because we'll use it as an index in shifting the **larger** elements in the sorted side to the right, in order to find the correct position for the `current` element.[^1] Then, we'll compare the value of `current` which is `3` to the value of `arr[j]` which is `5`. Since `5 > 3`, we'll shift `5` to the right. [^2] After the while-loop, we'll have the value of j as `-1` and the updated array would look like this : `[5, 5, 8, 6, 2]`. The next line of code after the loop will place the `current` at the newly found position which is `arr[j+1]`, in other words : `arr[0]`. The updated array would be like this : `[3,5,8,6,2]`. The process goes the same for sorting the remaining values in the array. 

`[3, 5, 8, 6, 2]` - Compare `5` with `8`, (Current is `8`, `arr[j]` is `5`). No shift needed [^3]
`[3, 5, 8, 6, 2]` - Compare `8` with `6`, (Current is `6`, `arr[j` is `8`). We'll be shifting `8` to the right to place `6` at its correct position. [^4]
`[3, 5, 6, 8, 2]` - Compare `8` with `2`, (Current is `2`, `arr[j` is `8`). We'll be shifting all the elements greater than `2` to the right. Namely, `3, 5, 6 and 8`. [^5]
Lastly `arr[j+1] = current` will finish the process off with the sorted array. `[2, 3, 5, 6, 8]`



[^1]: Due to `int current = arr[i];`. Since we are starting from the second element of the array. The for-loop will be `for (int i = 1; i < arr.length; i++){...}`. The value of `current` will change as the loop traverses.
[^2]: The loop `while(j >= 0 && arr[j] > current){...}` handles this case, since `j == 0` and `5 > 3`. The code in this loop will be executed. `arr[j+1] = arr[j];` First, 5 is shifted to the right. `[5, 5, 8, 6, 2]` will be the updated array. Next, we'll decrease the value of j, so we could get the correct position for the `current`. 
[^3]: Since `current` is greater than `arr[j]`, it will not satisfy this condition : `arr[j] > current`. Therefore, the loop will not get executed.
[^4]:Since `current` is less than `arr[j]`, it satisfies the condtion to execute the loop.
[^5]:Since `current` is less than `arr[j]`, it satisfies the condtion to execute the loop. First `8` will be shifted to the right. Then `6, 5 and 3` will be shifted and the array will be left with `[3, 3, 5, 6, 8]` and the value of j as `-1`.


