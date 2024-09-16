# **Binary Search Algorithm**
---

## *What is Binary Search?*

Binary Search is an efficient algorithm to find a target element in a **sorted array**. It operates by repeatedly dividing the search interval in half and comparing the middle element to the target value.

---
### ***How Does It Work?***

- It starts by comparing the element's value at the start of the array with the target value.
- If the value of the middle element is greater than the target value, it'll split the array in half and search the left half.
- If the value of the middle element is lower than the target value, it'll split the array in half and search the right half.
- Continue the above steps for new reduced portions of the array until the desired value is found.
- If the value is found, the code will return the index of the value in the array. If not, the code will return -1.

>*Binary Search requires that the input array is ==**sorted**== in non-decreasing order*

#### **Code preview**

```Java
  public static int binarySearch(int[] arr, int target){
        int left = 0;
        int right = arr.length - 1;
        
        while(left <= right){
            int mid = Math.floorDiv(left+right, 2);
            if(arr[mid] == target){
                return mid;
            } 
            if(arr[mid] < target){
                left = mid + 1;            
            }
            if(arr[mid] > target){
                right = mid - 1;
            }
        }
        return -1;
    }
```
##### **Time complexity**
- Best case : O(1)  `When the target is located at the array's middle`
- Average case : O(log n) 
- Worst case : O (log n) `When the target does not exist or when the target is at the extreme end of the array`

###### **Step-By-Step process**

Lets run the algorithm on the array `[1,2,4,5,6,10]` to search for target `2`.

**First iteration**
Set left to `0` and right to `5`. Since left is less than right, we shall compute the middle index of the array. In this case it will be `2`. [^1] The value of the middle index is `4`. Which triggers `true` for this condition : `arr[mid] > target`. So, the right value will be `1` [^2] So now, the first iteration is done. 

**Second iteration**
We'll compute the middle index with the new **right** value, which is `1`. The value of the middle index is `2` which triggers `true` for this condition : `arr[mid] == target`. So we have found the desired value so it will return `mid`. In this particular case we'll get `1`, since it's the index of `2` in the input array.


[^1]: Due to `Math.floorDiv(0 + 5, 2);`. Floor division is a division operation that rounds the result down to the nearest whole number or integer, which is less than or equal to the normal division result.
[^2]: Due to `right = mid - 1;`. In this case, mid is `2`. So `2 - 1 = 1`.




