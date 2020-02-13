package engine.csp.inference;


import engine.csp.CSP;
import engine.csp.Variable;

/**
 * Provides information about (1) whether changes have been performed, (2) possibly inferred empty domains , and
 * (3) how to restore the CSP.
 */

public interface InferenceLog<VAR extends Variable, VAL> {
    /**
     * Returns an empty inference log.
     */

    static <VAR extends Variable, VAL> InferenceLog<VAR, VAL> emptyLog() {
        return new InferenceLog<VAR, VAL>() {
            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public boolean inconsistencyFound() {
                return false;
            }

            @Override
            public void undo(CSP<VAR, VAL> csp) {
            }
        };
    }

    //returns true if no changes occurred in the CSP after inference procedure was applied
    boolean isEmpty();

    /*returns true if for a variable an "empty domain" was inferred.
    If an empty domain is inferred, it implies that no consistent assignment is possible for the CSP.*/
    boolean inconsistencyFound();

   /* this method is used to undo the inference carried out.
    That is, it restores the CSP to the state it was before the inference strategy was applied*/
    void undo(CSP<VAR, VAL> csp);

}
