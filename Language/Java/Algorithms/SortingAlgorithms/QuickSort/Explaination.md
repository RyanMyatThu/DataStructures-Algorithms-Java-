# Quick Sort Algorithm
---
## What Is Quick Sort?
The name suggests itself that it is one of the fastest sorting algorithms. It is highly effective and widely-used in arranging data. The algorithm uses the **Divide And Conquer** strategy.

>Quick Sort is known for its average-case time complexity of O(n log n) and its in-place sorting capability, meaning it doesn’t require extra space.

---
### How Does It Work?
 It works by selecting a **'pivot'** element from the array and partitioning the remaining elements into *two subarrays* : **elements less than the pivot and elements greater than the pivot**. The subarrays are then sorted **recursively** [^1].
 
 - Choose a pivot, in this case, the last element in the array. (Common strategies include picking the first, last, middle, or a random element.)
 - Parition the array, in such a way that all the elements on the left are smaller than the pivot and all elements greater than the pivot are on its right.
 - Recursively apply Quick Sort: Apply the same steps to the left and right subarrays.
 - Base case: When the array contains only one element or no elements, it's already sorted.

---

#### Code Preview
```Java
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
```

#### Algorithm Analysis
**Time Complexity** 
- Best Case : O(n log n) `When the pivot divides the array into two nearly equal halves.`
- Average Case : O(n log n)
- Worst Case : O(n^2^) `If the array is already sorted`

**Space Complexity**

O(log n) – Due to recursive calls in the algorithm. However, if recursion isn't optimized (tail recursion), it can degrade to O(n).

---

##### Step-By-Step Process

For example purposes, we'll sort the array : `[2, 4, 3, 5, 1]`
First of all, we'll determine the pivot, which is `1` in this case. Next, we'll move on to the partitioning process. In the `partition` method, we'll initialize the `pivot, i and j`. In this case, the pivot is `1`, i is `-1` and j is `0`[^2]. In the for-loop, we'll compare `arr[j]` to the pivot to see if `arr[j]` is less than or equal. The value of `arr[j]` is `2` and the pivot is `1`. Therefore `arr[j]` is greater than the `pivot`. So, we will not execute any swapping, instead incrementing `j`. After the increment, `j` becomes `1` and `i` stays the same.[^3] The value of `arr[j]` is now `4`. Since `4` > `1`, we'll continue to increment `j`. As we look forward, we could see that all the remaining elements are greater than `1` (3 and 5). So `j` will get incremented continuously until it equals `right`, which, by the time `j` would be `4` (`i` would still be `-1`) and the loop finally comes to an end. 

**Placing the pivot at the correct position**
After the loop, we'll swap the values of `pivot` and `arr[i+1]`, using a temporary variable `temp`. Lastly returning the index of the freshly swapped `pivot`. [^4]

**Recursive calls to quickSort method**
After determining the index of the pivot's correct position, we'll divide the array into two halves and call recursive `quickSort(...)` methods on both halves **recursively** until the array is sorted. [^5]
---
Now, `1` is in its correct position. Recursively apply Quick Sort on the two subarrays:
Left subarray: `[]` (empty, base case).
Right subarray: `[4, 3, 5, 2]`.

**Repeat for the right subarray**
Pivot: `2` (last element).
After partitioning, the array becomes `[1, 2, 3, 5, 4]`.
Recursively sort the left `[3]` and right `[5, 4]` subarrays.

**Final Sorted Array**: The result after all recursive calls complete will be `[1, 2, 3, 4, 5]`.

---

## Pivot Selection Strategies:

- Last Element: Common in this implementation.
- First Element: Can be efficient for unsorted data, but leads to poor performance on sorted data.
- Random: Helps avoid the worst-case scenario.
- Median: Attempts to pick a more balanced pivot.

**Recursion in Quick Sort:**
Recursion is the **key** to dividing and sorting the subarrays. The recursion continues until each subarray has only one or  zero elements, at which point the base case is reached, and the recursion unwinds.


[^1]: In computer science, recursion is a method of solving a computational problem where the solution depends on solutions to smaller instances of the same problem.
[^2]: Due to ` int pivot = arr[right];`, `int i = left - 1;` and `for (int j = left; j < right; j++) {...}`.
[^3]: We'll increment `i` only if `arr[j] <= pivot`, so we could swap `arr[i]` with `arr[j]`.
[^4]: `i = -1` so `i + 1 = 0` which is the correct index to place `1` which is the pivot.
[^5]: `quickSort(arr, left, pivot - 1)` is passing the array's left half into the method while `quickSort(arr, pivot + 1, right)` is passing the array's right half into the method. **Keep in mind that** : if a call such as `quickSort(arr, 0, -1)` is to be passed (Which is occurs in our case), the call will be removed from the call stack since it does not satisfy the base case which is `left < right`. We clearly know `0 is greater than -1`.