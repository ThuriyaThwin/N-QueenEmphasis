package engine.algo;

import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.CspSolver;
import engine.csp.Variable;
import util.Tasks;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BackjumpingSolver<VAR extends Variable, VAL> extends CspSolver<VAR,VAL> {
    @Override
    public Optional<Assignment> solve(CSP csp) {

        return Optional.empty();
    }




}
