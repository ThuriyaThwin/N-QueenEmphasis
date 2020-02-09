package test;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A specialization of the BacktrackingSolver to find a solution to the
 * N-queens problem. Includes a main method to print a solution to the
 * problem where the board size is given as a command line argument.
 */

public class QueensSolver extends BacktrackingSolver {
    /**
     * Determines if the queen was safely placed in column index by seeing if
     * its placement conflicts with any queen in columns 0..index-1.
     */
    protected boolean safeUpTo(int[] solution, int index) {
        int thisColumn = index;
        int thisRow = solution[index];
        for (int c = thisColumn-1; c >= 0; c--) {
            if (solution[c] == thisRow ||
                    solution[c] == thisRow + thisColumn - c ||
                    solution[c] == thisRow + c - thisColumn) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            int size = args.length < 1 ? 4 : Integer.parseInt(args[0]);
            System.out.println(Arrays.toString(new QueensSolver().solve(size)));
        } catch (NumberFormatException | NegativeArraySizeException e) {
            System.err.println("Argument should be a nonnegative integer");
        } catch (NoSuchElementException e) {
            System.err.println("No solutions for this board size");
        }
    }
}