# Mathematical Explanation of Heap Index Formulas

## Overview
In a **binary min heap** (or max heap) implemented using an array, we need formulas to calculate:
- Parent index from a child index
- Left child index from a parent index  
- Right child index from a parent index

## Array Representation
A complete binary tree is stored level by level, left to right in an array:

```
Level 0:                    [10]           → index 0
Level 1:            [20]              [30]    → indices 1, 2
Level 2:        [40]    [50]      [60]    [70]  → indices 3, 4, 5, 6
```

Array: `[10, 20, 30, 40, 50, 60, 70]`
Indices: ` 0,  1,  2,  3,  4,  5,  6`

## Formulas (0-indexed array)

### 1. Parent Index: `(index - 1) / 2` ✅

**Mathematical Derivation:**

Let's say we have a node at index `i` and its parent is at index `p`.

**Key observation:** In a complete binary tree:
- Level 0 has 2^0 = 1 node (index 0)
- Level 1 has 2^1 = 2 nodes (indices 1-2)
- Level 2 has 2^2 = 4 nodes (indices 3-6)
- Level k has 2^k nodes

**Finding which level a node is on:**
- Level 0: indices 0
- Level 1: indices 1 to 2
- Level 2: indices 3 to 6
- Level 3: indices 7 to 14

General formula: A node at index `i` is on level `k` where:
```
2^k - 1 ≤ i ≤ 2^(k+1) - 2
```

The starting index of level k is: `2^k - 1`
The number of nodes before level k is: `2^k - 1`

**Position within the level:**
If a node is at index `i` on level `k`:
- Starting index of level k = `2^k - 1`
- Position within level = `i - (2^k - 1)`

**Parent's position:**
- Parent is on level `k-1`
- Position of parent in its level = floor((position of child) / 2)
- Parent's index = (starting index of level k-1) + floor((position of child) / 2)

**Direct formula derivation:**
For a node at index `i`:
- The number of nodes before its level = `2^k - 1` where k is the level
- But we can work more directly:

If we have a complete binary tree:
- Node at index `i` has parent at approximately `i/2`
- But because we're 0-indexed, we need to adjust

**Visual proof:**
```
Index:   0   1   2   3   4   5   6
Parent:  -   0   0   1   1   2   2

Parent of 1: (1-1)/2 = 0/2 = 0 ✓
Parent of 2: (2-1)/2 = 1/2 = 0 ✓
Parent of 3: (3-1)/2 = 2/2 = 1 ✓
Parent of 4: (4-1)/2 = 3/2 = 1 ✓
Parent of 5: (5-1)/2 = 4/2 = 2 ✓
Parent of 6: (6-1)/2 = 5/2 = 2 ✓
```
---

### 2. Left Child Index: `(index * 2) + 1` ✅

**Mathematical Derivation:**

If a parent is at index `p`, its left child is the first child.

**Key insight:** In a complete binary tree stored in array:
- Each parent has exactly 2 children (except leaf nodes)
- Children are stored consecutively
- Left child comes before right child

**Visual proof:**
```
Parent at 0 → children at 1, 2
  Left child: 0*2 + 1 = 1 ✓
  Right child: 0*2 + 2 = 2 ✓

Parent at 1 → children at 3, 4
  Left child: 1*2 + 1 = 3 ✓
  Right child: 1*2 + 2 = 4 ✓

Parent at 2 → children at 5, 6
  Left child: 2*2 + 1 = 5 ✓
  Right child: 2*2 + 2 = 6 ✓
```

**Why `+1` and not just `*2`?**
- If we used `index * 2`:
  - Parent 0 → child at 0*2 = 0 (same as parent!) ✗
  - Parent 1 → child at 1*2 = 2 (skips index 1!) ✗
  
- We need `+1` because arrays are 0-indexed:
  - Parent 0 should have left child at 1 (not 0)
  - The `+1` accounts for the 0-based indexing offset

---

### 3. Right Child Index: `(index * 2) + 2` ✅

**Mathematical Derivation:**

Similar to left child, but since right child comes after left child, we add one more.

**Visual proof:**
```
Parent at 0 → right child at 2: 0*2 + 2 = 2 ✓
Parent at 1 → right child at 4: 1*2 + 2 = 4 ✓
Parent at 2 → right child at 6: 2*2 + 2 = 6 ✓
```

**Why `+2` and not `+1`?**
- Left child is at `(index * 2) + 1`
- Right child immediately follows left child
- Therefore: right child = left child + 1 = `(index * 2) + 1 + 1` = `(index * 2) + 2`

---

## Complete Example

Let's trace through an example with array: `[10, 20, 30, 40, 50, 60, 70]`

```
Tree structure:
          10 (index 0)
        /    \
   20 (1)   30 (2)
   /  \     /  \
40(3) 50(4) 60(5) 70(6)
```

**Finding parent of node at index 4 (value 50):**
- Formula: `(4 - 1) / 2 = 3 / 2 = 1`
- Parent is at index 1 (value 20) ✓

**Finding children of node at index 1 (value 20):**
- Left child: `(1 * 2) + 1 = 2 + 1 = 3` → value 40 ✓
- Right child: `(1 * 2) + 2 = 2 + 2 = 4` → value 50 ✓

---

## Summary

| Operation | Formula (0-indexed) | Formula (1-indexed) |
|-----------|---------------------|---------------------|
| Parent    | `(i - 1) / 2`       | `i / 2`             |
| Left      | `(i * 2) + 1`       | `i * 2`             |
| Right     | `(i * 2) + 2`       | `i * 2 + 1`         |

**Important Note:** The correct parent formula for 0-indexed arrays is `(index - 1) / 2`, **not** `(index - 2) / 2`. The `-2` formula would only work correctly in special cases and is mathematically incorrect for general heap operations.
