package org.example;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
//        System.out.print("Hello and welcome!");
//
//        for (int i = 1; i <= 5; i++) {
//            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
//            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
//            System.out.println("i = " + i);
//        }


        System.out.println(solution("aaabab", 0)); // Output: 1
        System.out.println(solution("aaabab", 6)); // Output: 5
        System.out.println(solution("ababa", 1)); // Output: -1
        System.out.println(solution("babbaa", 2)); // Output: 0
    }

    public static int solution(String L, int start) {
        int N = L.length();
        char[] board = L.toCharArray();

        int aCount = 0, bCount = 0;
        for (char ch : board) {
            if (ch == 'a') aCount++;
            else bCount++;
        }

        // If already balanced, return 0
        if (aCount == bCount) return 0;

        Queue<int[]> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(new int[]{start, 0, aCount, bCount}); // {position, moves, aCount, bCount}
        visited.add(new String(board));

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int pos = curr[0], moves = curr[1], currentACount = curr[2], currentBCount = curr[3];

            for (int dir : new int[]{-1, 1}) {
                int newPos = pos + dir;
                if (newPos < 0 || newPos >= N + 1) continue;

                // Flip the letter at min(pos, newPos)
                char[] newBoard = board.clone();
                int flipIndex = Math.min(pos, newPos);
                if (newBoard[flipIndex] == 'a') {
                    newBoard[flipIndex] = 'b';
                    currentACount--;
                    currentBCount++;
                } else {
                    newBoard[flipIndex] = 'a';
                    currentACount++;
                    currentBCount--;
                }

                // Check if balanced
                if (currentACount == currentBCount) return moves + 1;

                // Store new state as a string
                String newState = new String(newBoard);
                if (!visited.contains(newState)) {
                    queue.offer(new int[]{newPos, moves + 1, currentACount, currentBCount});
                    visited.add(newState);
                }
            }
        }

        return -1; // If no valid solution
    }
}