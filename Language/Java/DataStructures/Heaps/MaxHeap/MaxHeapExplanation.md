# Max Heap Implementation Explanation

## Overview

A **Max Heap** (also called a maximum heap) is a complete binary tree data structure where:
- The root node contains the maximum value
- Every parent node has a value **greater than or equal to** its children
- The tree is **complete** (filled from left to right at each level)
- Implemented efficiently using an **array**

## Max Heap Property

**For every node `i` (except root):**
```
items[parent(i)] ≥ items[i]
```

**Visual Example:**
```
          50
        /    \
      30      40
     /  \    /  \
   20   25  35   10
```

Array representation: `[50, 30, 40, 20, 25, 35, 10]`

## Array Representation

A complete binary tree is stored level-by-level, left-to-right in an array:

```
Level 0:              [50]                  → index 0
Level 1:        [30]          [40]          → indices 1, 2
Level 2:    [20]    [25]  [35]    [10]      → indices 3, 4, 5, 6
```

**Key Formulas (0-indexed array):**
- **Parent index:** `(childIndex - 1) / 2`
- **Left child index:** `(parentIndex * 2) + 1`
- **Right child index:** `(parentIndex * 2) + 2`

---

## Core Operations

### 1. `add(int item)` - Insert Element

**Time Complexity:** O(log n)

**Steps:**
1. Ensure capacity (resize array if needed)
2. Add element at the end (`items[size] = item`)
3. Increment size
4. **Heapify Up** to restore heap property

**Example:**
Adding `45` to heap `[50, 30, 40, 20, 25, 35, 10]`:
```
Initial:       50              After adding 45 at end:
             /    \                    50
           30      40                /    \
          /  \    /  \             30      40
        20   25  35   10          /  \    /  \
                               20   25  35   10
                                            \
                                            45 (at index 7)

Heapify Up: Compare 45 with parent(10)
- 45 > 10, so swap:                45 > 40, so swap again:
       50                               50
      /  \                             /  \
    30    45                         30    45
   / \   / \                        / \   / \
 20 25  40 10                     20 25 40 35
                                  /
                                10

Final: [50, 30, 45, 20, 25, 40, 35, 10]
```

### 2. `poll()` - Remove Maximum Element

**Time Complexity:** O(log n)

**Steps:**
1. Return root element (maximum value)
2. Move last element to root (`items[0] = items[size-1]`)
3. Decrement size
4. **Heapify Down** to restore heap property

**Example:**
Removing maximum from heap `[50, 30, 40, 20, 25, 35, 10]`:
```
Initial:       50              After moving last element to root:
             /    \                    10
           30      40                /    \
          /  \    /  \             30      40
        20   25  35   10          /  \    /
                               20   25  35

Heapify Down: Compare 10 with children(30, 40)
- max(30, 40) = 40, 10 < 40, so swap:     10 < 35, so swap again:
       40                                  40
      /  \                                /  \
    30    10                            30   35
   / \    /                             / \
 20 25   35                           20 25 10

Final: [40, 30, 35, 20, 25, 10]
```

### 3. `peek()` - Get Maximum Element (without removing)

**Time Complexity:** O(1)

Simply returns `items[0]` which is always the maximum in a max heap.

---

## Helper Methods

### Index Calculations

#### `getParentIndex(int childIndex)`
Returns the index of the parent node.
- Formula: `(childIndex - 1) / 2`
- Example: Parent of index 5 = `(5-1)/2 = 2`

#### `getLeftChildIndex(int parentIndex)`
Returns the index of the left child.
- Formula: `(parentIndex * 2) + 1`
- Example: Left child of index 2 = `(2*2)+1 = 5`

#### `getRightChildIndex(int parentIndex)`
Returns the index of the right child.
- Formula: `(parentIndex * 2) + 2`
- Example: Right child of index 2 = `(2*2)+2 = 6`

### Existence Checks

#### `hasParent(int childIndex)`
Returns `true` if the node has a parent (i.e., it's not the root).
- Root is at index 0, so `childIndex > 0` means it has a parent

#### `hasLeftChild(int parentIndex)`
Returns `true` if left child exists (index is within bounds).
- Checks if `(parentIndex * 2) + 1 < size`

#### `hasRightChild(int parentIndex)`
Returns `true` if right child exists (index is within bounds).
- Checks if `(parentIndex * 2) + 2 < size`

---

## Heap Restoration Algorithms

### `heapifyUp()` - Restore Heap Property Upward

**When used:** After adding a new element.

**Algorithm:**
1. Start at the newly added element (last index: `size - 1`)
2. While the element has a parent AND parent < current element:
   - Swap with parent
   - Move up to parent position
3. Stop when heap property is satisfied or reached root

**Code Logic:**
```java
int index = size - 1;  // Start at last element
while(hasParent(index) && items[getParentIndex(index)] < items[index]) {
    swap(getParentIndex(index), index);  // Swap with parent
    index = getParentIndex(index);        // Move up
}
```

**Why `items[getParentIndex(index)] < items[index]`?**
- In a max heap, parent should be ≥ child
- If `parent < child`, we need to swap
- After swapping, the larger value moves up

### `heapifyDown()` - Restore Heap Property Downward

**When used:** After removing root (poll operation).

**Algorithm:**
1. Start at root (index 0)
2. While current node has a left child:
   - Find the greater of left and right children
   - If greater child > current node:
     - Swap with greater child
     - Move down to that child's position
   - Else: stop (heap property satisfied)

**Code Logic:**
```java
int index = 0;  // Start at root
while(hasLeftChild(index)) {
    int greaterChildIndex = getLeftChildIndex(index);
    // Check if right child exists and is greater
    if(hasRightChild(index) && items[getRightChildIndex(index)] > items[greaterChildIndex]) {
        greaterChildIndex = getRightChildIndex(index);
    }
    // If heap property satisfied, break
    if(items[greaterChildIndex] < items[index]) {
        break;
    } else {
        swap(greaterChildIndex, index);  // Swap with greater child
        index = greaterChildIndex;        // Move down
    }
}
```

---

## Dynamic Array Resizing

### `ensureExtraSpace()`

**When used:** Before adding new elements.

**Strategy:** Double the capacity when array is full.

```java
if(size == capacity) {
    items = Arrays.copyOf(items, capacity * 2);  // Double size
    capacity *= 2;
}
```

**Amortized Time Complexity:** O(1) per insertion
- Most insertions are O(1)
- Occasional O(n) resize is amortized across many operations

---

## Complete Example: Building a Max Heap

Let's build a max heap by adding elements: `[8, 16, 10, 15, 30, 50, 20]`

```
Step 1: Add 8
[8]

Step 2: Add 16
[8] → swap with 16
[16, 8]

Step 3: Add 10
[16, 8] → add 10 at end
[16, 8, 10] → 10 < 16, no swap needed
[16, 8, 10]

Step 4: Add 15
[16, 8, 10, 15] → 15 > 8, swap
[16, 15, 10, 8] → 15 < 16, stop
[16, 15, 10, 8]

Step 5: Add 30
[16, 15, 10, 8, 30] → 30 > 15, swap
[16, 30, 10, 8, 15] → 30 > 16, swap
[30, 16, 10, 8, 15]

Step 6: Add 50
[30, 16, 10, 8, 15, 50] → 50 > 10, swap
[30, 16, 50, 8, 15, 10] → 50 > 30, swap
[50, 16, 30, 8, 15, 10]

Step 7: Add 20
[50, 16, 30, 8, 15, 10, 20] → 20 > 8, swap
[50, 16, 30, 20, 15, 10, 8] → 20 > 16, swap
[50, 20, 30, 16, 15, 10, 8] → 20 < 50, stop
Final: [50, 20, 30, 16, 15, 10, 8]
```

Visual representation:
```
          50
        /    \
      20      30
     /  \    /  \
   16   15  10   8
```

---

## Time Complexity Summary

| Operation | Time Complexity | Notes |
|-----------|----------------|-------|
| `peek()` | O(1) | Root is always at index 0 |
| `add()` | O(log n) | Heapify up: max height = log n |
| `poll()` | O(log n) | Heapify down: max height = log n |
| `getSize()` | O(1) | Simply returns size variable |

**Space Complexity:** O(n) for storing n elements

---

## Use Cases

1. **Priority Queues**: Always get maximum priority element
2. **Heap Sort**: Sort elements in O(n log n) time (descending order)
3. **Graph Algorithms**: 
   - Finding maximum paths
   - Maximum spanning tree
4. **Scheduling**: Process longest jobs first
5. **Event Simulation**: Process events in reverse chronological order
6. **Finding K Largest Elements**: Efficiently find top K elements in a collection

---

## Implementation Notes

### ✅ Correct Implementation Details

1. **0-indexed array**: All formulas account for 0-based indexing
2. **Bounds checking**: Helper methods check before accessing
3. **Dynamic resizing**: Array doubles when full
4. **Max heap property**: Parent ≥ children enforced correctly
5. **No circular dependencies**: Helper methods avoid calling each other recursively

### Key Implementation Highlights

- Uses `Math.floorDiv()` for parent calculation (handles negative correctly, though not needed here)
- Private helper methods for clean code organization
- Bounds checking prevents `ArrayIndexOutOfBoundsException`
- Clear separation between public API and internal operations
- Fixed `hasParent()`, `hasLeftChild()`, and `hasRightChild()` to avoid circular dependencies

---

## Differences from Min Heap

| Aspect | Min Heap | Max Heap |
|--------|----------|----------|
| Root value | Minimum | Maximum |
| Heap property | Parent ≤ Children | Parent ≥ Children |
| `peek()` returns | Smallest element | Largest element |
| `poll()` removes | Smallest element | Largest element |
| `heapifyUp()` condition | `parent > child` | `parent < child` |
| `heapifyDown()` comparison | Find smaller child | Find greater child |

---

## Common Mistakes to Avoid

1. **Wrong comparison in heapifyUp**: 
   - ❌ `items[getParentIndex(index)] > items[index]` (swaps when already correct)
   - ✅ `items[getParentIndex(index)] < items[index]` (swaps when wrong)

2. **Wrong comparison in heapifyDown**: 
   - ❌ `items[greaterChildIndex] > items[index]` then break (would break when should swap)
   - ✅ `items[greaterChildIndex] < items[index]` then break (breaks when heap property satisfied)

3. **Forgetting to update index after swap**: Must move to new position

4. **Not checking if children exist**: In heapifyDown, check `hasLeftChild` first

5. **Array index errors**: Remember 0-indexed formulas are different from 1-indexed

6. **Circular dependencies**: `hasParent()`, `hasLeftChild()`, and `hasRightChild()` should not call the get index methods to avoid infinite recursion

---

## Testing the Implementation

Example test sequence:
```java
Heap heap = new Heap();

heap.add(10);
heap.add(30);
heap.add(20);
heap.add(50);
heap.add(15);

System.out.println(heap.peek());  // Should print: 50

int max = heap.poll();             // Removes 50
System.out.println(heap.peek());  // Should print: 30

heap.add(60);
System.out.println(heap.peek());  // Should print: 60
```

---

## References

- Standard max heap implementation following array-based complete binary tree representation
- For mathematical derivation of index formulas, see MinHeap's `HeapMathExplanation.md` (same formulas apply)