# Min Heap Implementation Explanation

## Overview

A **Min Heap** (also called a minimum heap) is a complete binary tree data structure where:
- The root node contains the minimum value
- Every parent node has a value **less than or equal to** its children
- The tree is **complete** (filled from left to right at each level)
- Implemented efficiently using an **array**

## Min Heap Property

**For every node `i` (except root):**
```
items[parent(i)] ≤ items[i]
```

**Visual Example:**
```
           3
         /   \
       10     15
      /  \   /  \
    20   25 30   40
```

Array representation: `[3, 10, 15, 20, 25, 30, 40]`

## Array Representation

A complete binary tree is stored level-by-level, left-to-right in an array:

```
Level 0:              [3]                  → index 0
Level 1:        [10]          [15]          → indices 1, 2
Level 2:    [20]    [25]  [30]    [40]      → indices 3, 4, 5, 6
```

**Key Formulas (0-indexed array):**
- **Parent index:** `(childIndex - 1) / 2`
- **Left child index:** `(parentIndex * 2) + 1`
- **Right child index:** `(parentIndex * 2) + 2`

For detailed mathematical explanation, see `HeapMathExplanation.md`.

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
Adding `5` to heap `[3, 10, 15, 20, 25, 30, 40]`:
```
Initial:       3              After adding 5 at end:
             /   \                    3
           10     15                /   \
          /  \   /  \             10     15
        20   25 30   40          /  \   /  \
                               20   25 30   40
                                              \
                                               5 (at index 7)

Heapify Up: Compare 5 with parent(15)
- 5 < 15, so swap:                5 < 3, so swap again:
        3                               5
       / \                             / \
     10   5                         3    10
    / \  / \                       / \  / \
  20 25 15 40                    20 25 15 40

Final: [5, 3, 10, 20, 25, 15, 40]
```

### 2. `poll()` - Remove Minimum Element

**Time Complexity:** O(log n)

**Steps:**
1. Return root element (minimum value)
2. Move last element to root (`items[0] = items[size-1]`)
3. Decrement size
4. **Heapify Down** to restore heap property

**Example:**
Removing minimum from heap `[3, 10, 15, 20, 25, 30, 40]`:
```
Initial:       3              After moving last element to root:
             /   \                    40
           10     15                /   \
          /  \   /  \             10     15
        20   25 30   40          /  \   /
                               20   25 30

Heapify Down: Compare 40 with children(10, 15)
- min(10, 15) = 10, 40 > 10, so swap:     40 > 25, so swap again:
        10                                  10
       /  \                                /  \
     40    15                            25    15
    /  \   /                            /  \   /
  20   25 30                          20   40 30

Final: [10, 20, 15, 25, 40, 30]
```

### 3. `peek()` - Get Minimum Element (without removing)

**Time Complexity:** O(1)

Simply returns `items[0]` which is always the minimum in a min heap.

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
- Root is at index 0, so `getParentIndex(0) = -1` → no parent

#### `hasLeftChild(int parentIndex)`
Returns `true` if left child exists (index is within bounds).
- Checks if `getLeftChildIndex(parentIndex) < size`

#### `hasRightChild(int parentIndex)`
Returns `true` if right child exists (index is within bounds).
- Checks if `getRightChildIndex(parentIndex) < size`

### Value Accessors

#### `parent(int childIndex)`
Returns the value of the parent node (with bounds checking).

#### `leftChild(int parentIndex)`
Returns the value of the left child (with bounds checking).

#### `rightChild(int parentIndex)`
Returns the value of the right child (with bounds checking).

---

## Heap Restoration Algorithms

### `heapifyUp()` - Restore Heap Property Upward

**When used:** After adding a new element.

**Algorithm:**
1. Start at the newly added element (last index)
2. While the element has a parent AND parent > current element:
   - Swap with parent
   - Move up to parent position
3. Stop when heap property is satisfied or reached root

**Code Logic:**
```java
int index = size - 1;  // Start at last element
while(hasParent(index) && parent(index) > items[index]) {
    swap(index, getParentIndex(index));  // Swap with parent
    index = getParentIndex(index);        // Move up
}
```

**Why `parent(index) > items[index]`?**
- In a min heap, parent should be ≤ child
- If `parent > child`, we need to swap
- After swapping, the smaller value moves up

### `heapifyDown()` - Restore Heap Property Downward

**When used:** After removing root (poll operation).

**Algorithm:**
1. Start at root (index 0)
2. While current node has a left child:
   - Find the smaller of left and right children
   - If smaller child < current node:
     - Swap with smaller child
     - Move down to that child's position
   - Else: stop (heap property satisfied)

**Code Logic:**
```java
int index = 0;  // Start at root
while(hasLeftChild(index)) {
    int smallerChildIndex = getLeftChildIndex(index);
    // Check if right child exists and is smaller
    if(hasRightChild(index) && leftChild(index) > rightChild(index)) {
        smallerChildIndex = getRightChildIndex(index);
    }
    // If heap property satisfied, break
    if(items[smallerChildIndex] > items[index]) {
        break;
    } else {
        swap(index, smallerChildIndex);  // Swap with smaller child
        index = smallerChildIndex;        // Move down
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

## Complete Example: Building a Min Heap

Let's build a min heap by adding elements: `[50, 30, 20, 15, 10, 8, 16]`

```
Step 1: Add 50
[50]

Step 2: Add 30
[50] → swap with 30
[30, 50]

Step 3: Add 20
[30, 50] → add 20 at end
[30, 50, 20] → 20 < 30, swap
[20, 50, 30]

Step 4: Add 15
[20, 50, 30, 15] → 15 < 50, swap
[20, 15, 30, 50] → 15 < 20, swap
[15, 20, 30, 50]

Step 5: Add 10
[15, 20, 30, 50, 10] → 10 < 20, swap
[15, 10, 30, 50, 20] → 10 < 15, swap
[10, 15, 30, 50, 20]

Step 6: Add 8
[10, 15, 30, 50, 20, 8] → 8 < 30, swap
[10, 15, 8, 50, 20, 30] → 8 < 15, swap
[10, 8, 15, 50, 20, 30] → 8 < 10, swap
[8, 10, 15, 50, 20, 30]

Step 7: Add 16
[8, 10, 15, 50, 20, 30, 16] → 16 < 50, swap
[8, 10, 15, 16, 20, 30, 50] → 16 > 15, stop
Final: [8, 10, 15, 16, 20, 30, 50]
```

Visual representation:
```
          8
        /   \
      10     15
     /  \   /  \
   16   20 30   50
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

1. **Priority Queues**: Always get minimum priority element
2. **Heap Sort**: Sort elements in O(n log n) time
3. **Graph Algorithms**: 
   - Dijkstra's shortest path
   - Prim's minimum spanning tree
4. **Scheduling**: Process shortest jobs first
5. **Event Simulation**: Process events in chronological order

---

## Implementation Notes

### ✅ Correct Implementation Details

1. **0-indexed array**: All formulas account for 0-based indexing
2. **Bounds checking**: Helper methods check before accessing
3. **Dynamic resizing**: Array doubles when full
4. **Min heap property**: Parent ≤ children enforced correctly

### Key Implementation Highlights

- Uses `Math.floorDiv()` for parent calculation (handles negative correctly, though not needed here)
- Private helper methods for clean code organization
- Bounds checking prevents `ArrayIndexOutOfBoundsException`
- Clear separation between public API and internal operations

---

## Common Mistakes to Avoid

1. **Wrong comparison in heapifyUp**: 
   - ❌ `parent(index) < items[index]` (swaps when already correct)
   - ✅ `parent(index) > items[index]` (swaps when wrong)

2. **Forgetting to update index after swap**: Must move to new position

3. **Not checking if children exist**: In heapifyDown, check `hasLeftChild` first

4. **Array index errors**: Remember 0-indexed formulas are different from 1-indexed

---

## Testing the Implementation

Example test sequence:
```java
Heap heap = new Heap();

heap.add(50);
heap.add(30);
heap.add(20);
heap.add(15);
heap.add(10);

System.out.println(heap.peek());  // Should print: 10

int min = heap.poll();             // Removes 10
System.out.println(heap.peek());  // Should print: 15

heap.add(5);
System.out.println(heap.peek());  // Should print: 5
```

---

## References

- For mathematical derivation of index formulas, see `HeapMathExplanation.md`
- Standard min heap implementation following array-based complete binary tree representation
