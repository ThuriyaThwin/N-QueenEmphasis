package test;

import java.util.Optional;

/**
 * An engine for solving problems with the backtracking paradigm.
 *
 * <p>Solve a particular problem by subclassing this class, implementing
 * the method that determines whether a partial solution is conflict-free
 * so far.
 */
public abstract class BacktrackingSolver {

    /**
     * Subclass-specific method that returns whether or not the partial
     * solution in slots 0..position-1 is okay so far.
     */
    protected abstract boolean safeUpTo(int[] solution, int position);

    /**
     * Returns a solution to the problem, or throws a NoSuchElementException
     * if there is no solution.
     */
    public final int[] solve(int solutionLength) {
        return extendSolution(new int[solutionLength], 0).get();
    }

    /**
     * The fixed "engine" that is not overridden in subclasses.
     */
    private Optional<int[]> extendSolution(int[] solution, int position) {
        for (int value = 0; value < solution.length; value++) {
            solution[position] = value;
            if (safeUpTo(solution, position)) {
                if (position >= solution.length-1 || extendSolution(solution, position+1).isPresent()) {
                    return Optional.of(solution);
                }
            }
        }
        return Optional.empty();
    }
}