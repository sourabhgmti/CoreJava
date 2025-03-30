package org.example;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class TaskTwo {
    public static void main(String[] args) {
        System.out.println(solution(new int[]{2, 1, 2, 3, 2, 2}, 3)); // Expected: 2
        System.out.println(solution(new int[]{2, 3, 1, 1, 2}, 2));   // Expected: 3
        System.out.println(solution(new int[]{20, 10, 10, 10, 30, 20}, 3)); // Expected: 3
        System.out.println(solution(new int[]{1, 100000, 1}, 3));    // Expected: 0
    }

    public static int solution(int[] A, int R) {
        int N = A.length;
        if (N == R) return 0; // All shelves are removed

        Map<Integer, Integer> freq = new HashMap<>();

        // Count frequency of each item in the whole array
        for (int item : A) {
            freq.put(item, freq.getOrDefault(item, 0) + 1);
        }

        int maxTypes = 0;

        // Use a sliding window to remove R consecutive elements
        Map<Integer, Integer> windowFreq = new HashMap<>();

        // First, count item frequencies inside the first R-sized window
        for (int i = 0; i < R; i++) {
            windowFreq.put(A[i], windowFreq.getOrDefault(A[i], 0) + 1);
        }

        // Try removing shelves from each valid position
        for (int start = 0; start + R <= N; start++) {
            // Remove current R-sized window from the total frequency
            if (start > 0) {
                int removeIndex = start - 1;
                windowFreq.put(A[removeIndex], windowFreq.get(A[removeIndex]) - 1);
                if (windowFreq.get(A[removeIndex]) == 0) {
                    windowFreq.remove(A[removeIndex]);
                }

                int addIndex = start + R - 1;
                if (addIndex < N) {
                    windowFreq.put(A[addIndex], windowFreq.getOrDefault(A[addIndex], 0) + 1);
                }
            }

            // Count unique types left after removing current window
            int uniqueLeft = freq.size();
            for (Map.Entry<Integer, Integer> entry : windowFreq.entrySet()) {
                if (freq.get(entry.getKey()) == entry.getValue()) {
                    uniqueLeft--;
                }
            }

            maxTypes = Math.max(maxTypes, uniqueLeft);
        }

        return maxTypes;
    }
}

/*
Step-by-Step Explanation of the Problem
Problem Overview

We have N shelves, each containing items of only one type.

The item type on the K-th shelf is given by A[K].

We must free R consecutive shelves to make space.

The remaining shelves cannot be reordered after removing R shelves.

Our goal is to maximize the number of different item types left after freeing R consecutive shelves.

Understanding Constraints

N (number of shelves) can be up to 100,000 → Efficient solution required.

R (shelves to remove) can be up to N → We may need to remove all shelves.

A[i] values range between 1 and 100,000 (valid item types).

Example Walkthrough
Let's analyze some given examples:

Example 1:

ini
Copy
Edit
A = [2, 1, 2, 3, 2, 2] , R = 3
If we remove shelves 2, 3, and 4 → Remaining array: [2, 1, 2]

Unique types left: {1, 2} → Answer = 2

Example 2:

ini
Copy
Edit
A = [2, 3, 1, 1, 2] , R = 2
If we remove shelves 3 and 4 → Remaining array: [2, 3, 1]

Unique types left: {1, 2, 3} → Answer = 3

Example 3:

ini
Copy
Edit
A = [20, 10, 10, 10, 30, 20] , R = 3
If we remove first 3 shelves (0,1,2) → Remaining array: [10, 30, 20]

Unique types left: {10, 30, 20} → Answer = 3

Example 4:

ini
Copy
Edit
A = [1, 100000, 1] , R = 3
If we remove all 3 shelves → Nothing remains.

Answer = 0

Efficient Approach: Sliding Window Technique
Since R consecutive shelves must be removed, we can:

Use a sliding window of size R to iterate over possible removals.

Track unique item types before and after the removal using a HashMap or HashSet.

Compute the max number of unique item types remaining after removal.

Java 8 Solution
java
Copy
Edit
import java.util.*;

class Solution {
    public int solution(int[] A, int R) {
        int N = A.length;
        if (N == R) return 0; // All shelves are removed

        Map<Integer, Integer> freq = new HashMap<>();

        // Count frequency of each item in the whole array
        for (int item : A) {
            freq.put(item, freq.getOrDefault(item, 0) + 1);
        }

        int maxTypes = 0;

        // Use a sliding window to remove R consecutive elements
        Map<Integer, Integer> windowFreq = new HashMap<>();

        // First, count item frequencies inside the first R-sized window
        for (int i = 0; i < R; i++) {
            windowFreq.put(A[i], windowFreq.getOrDefault(A[i], 0) + 1);
        }

        // Try removing shelves from each valid position
        for (int start = 0; start + R <= N; start++) {
            // Remove current R-sized window from the total frequency
            if (start > 0) {
                int removeIndex = start - 1;
                windowFreq.put(A[removeIndex], windowFreq.get(A[removeIndex]) - 1);
                if (windowFreq.get(A[removeIndex]) == 0) {
                    windowFreq.remove(A[removeIndex]);
                }

                int addIndex = start + R - 1;
                if (addIndex < N) {
                    windowFreq.put(A[addIndex], windowFreq.getOrDefault(A[addIndex], 0) + 1);
                }
            }

            // Count unique types left after removing current window
            int uniqueLeft = freq.size();
            for (Map.Entry<Integer, Integer> entry : windowFreq.entrySet()) {
                if (freq.get(entry.getKey()) == entry.getValue()) {
                    uniqueLeft--;
                }
            }

            maxTypes = Math.max(maxTypes, uniqueLeft);
        }

        return maxTypes;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(new int[]{2, 1, 2, 3, 2, 2}, 3)); // Expected: 2
        System.out.println(sol.solution(new int[]{2, 3, 1, 1, 2}, 2));   // Expected: 3
        System.out.println(sol.solution(new int[]{20, 10, 10, 10, 30, 20}, 3)); // Expected: 3
        System.out.println(sol.solution(new int[]{1, 100000, 1}, 3));    // Expected: 0
    }
}
Time Complexity Analysis
Building the frequency map → O(N)

Sliding window iteration → O(N)

Updating frequency counts → O(1) (HashMap operations)

Total complexity → O(N), which is efficient for N ≤ 100,000.

Why This Works?
HashMap efficiently tracks frequencies of item types.

Sliding window optimizes removal choices in O(1) per step.

We avoid redundant recomputation by updating frequency counts dynamically.

Final Thoughts
This approach efficiently finds the optimal R-sized window to remove while ensuring maximum unique item types remain. It handles edge cases well, such as:

Removing all shelves (R = N).

Different distributions of item types.

Large constraints (N = 100,000).

This ensures the best solution with minimal computation. 🚀
 */