def MergeSort(arr):
    if arr is None or len(arr) <= 1:
        return arr
   
    mid = len(arr) // 2
    left_subArr = arr[:mid]
    right_subArr = arr[mid:]
    
    left_subArr = MergeSort(left_subArr)
    right_subArr = MergeSort(right_subArr)
    return Merge(left_subArr, right_subArr, arr)

def Merge(left, right, arr):
  
    i = 0
    j = 0
    k = 0

    while (i < len(left) and j < len(right)):
        if( left[i] <= right[j]):
            arr[k] = left[i]
            i += 1
        else:
            arr[k] = right[j]
            j += 1
        k += 1
    while(i < len(left)):
        arr[k] = left[i]
        i += 1
        k += 1
    while(j < len(right)):
        arr[k] = right[j]
        j += 1
        k += 1
    return arr

array = [38, 27, 43, 3, 9, 82, 10]
sorted_array = MergeSort(array)
print("Sorted array is:", sorted_array)