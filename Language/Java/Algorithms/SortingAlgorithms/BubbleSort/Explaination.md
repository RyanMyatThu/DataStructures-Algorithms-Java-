## Bubble Sort Algorithm

---

## What is bubble sort?

Bubble Sort is a straightforward comparison-based sorting algorithm. It works by repeatedly "bubbling" the largest unsorted element to the end of the list, much like how bubbles rise to the top. In each pass, adjacent elements are compared, and if they are in the wrong order, they are swapped. This process continues until no swaps are needed, indicating that the list is sorted.


>Bubble sort is not the most efficient sorting algorithm, as it has a time complexity of 𝑂(𝑛^2^), but its simplicity makes it an excellent choice for educational purposes and small datasets.
---

## How does bubble sort work?

Here's how it works step-by-step:

- Start from the beginning of the list and compare each pair of adjacent elements.

- If the current element is greater than the next element, swap them. This way, the largest unsorted element "bubbles" to the end of the list.

- Move back to the beginning of the list and repeat this process, ignoring the sorted portion at the end.

- Continue until a complete pass occurs with no swaps, which means the list is sorted.

---

## Code Preview

```java

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
      // If no swaps were performed in the inner iteration we will stop the outer loop
      if(!swapped){
        break;
      }
    }
}
```

---

## Step-By-Step Explaination

Let's consider an array `[5, 4, 3, 2, 1]`, is to be sorted using bubble sort algorithm.

First we start our outer loop, then we'll compare the adjacent elements during the inner loop's iteration to check if a swap is needed.

First, the largest element which is `5` will bubble towards the sorted portion; here is how the array would look like : `[4, 3, 2, 1, 5]`

After the second iteration : `[3, 2, 1, 4, 5]`

After the thrid iteration : `[2, 1, 3, 4, 5]`

After the fourth iteration : `[1, 2, 3, 4, 5]`

With the help of the `swapped` flag the outer loop will end. Therefore, our process is finished.

---

## Time Complexity

| Case | Time Complexity |
| ---  |  ---            |
| Best Case | O(n^2^)
| Average Case | O(n^2^)
| Worst Case | O(n^2^)

---

## Space Complexity

Bubble Sort is an in-place sorting algorithm, which means it requires a constant amount of additional memory, irrespective of the size of the input array.

Space Complexity: O(1)

Bubble Sort only requires a few additional variables (like swap and temp for swapping elements), which means it has a space complexity of O(1).