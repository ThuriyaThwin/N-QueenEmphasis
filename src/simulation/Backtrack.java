package simulation;

import engine.algo.AbstractBacktrackingSolver;
import engine.algo.CspHeuristics;
import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.*;
import engine.csp.inference.AC3Strategy;
import engine.csp.inference.ForwardCheckingStrategy;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import nqueens.*;
import nqueens.view.NQueensBoard;
import nqueens.view.Parameter;
import util.StoreResult;
import util.Util;
import util.XYLocation;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Integrable application which demonstrates how different CSP solution
 * strategies solve the N-Queens problem.
 **/

public class Backtrack extends IntegrableApplication {

    private final static String SOLUTION="solution =";
    private final static String PARAM_STRATEGY = "";
    private final static String PARAM_BOARD_SIZE = "b = ";
    private final static String POSITION="position = ";
    private NQueensViewCtrl stateViewCtrl;
    private TaskExecutionPaneCtrl taskPaneCtrl;
    private CSP<Variable, Integer> csp;
    private CspSolver<Variable, Integer> solver;
    private CspListener.StepCounter<Variable, Integer> stepCounter = new CspListener.StepCounter<>();
    FlexibleBacktrackingSolver<Variable, Integer> bSolver=new FlexibleBacktrackingSolver<>();

    public static String size;


    public StoreResult storeResult=new StoreResult();
    private String algorithmName;

    @Override
    public String getTitle() {
        return "N-Queens CSP";
    }

    /**
     * Defines state model.nqueen.view, parameters, and call-back functions and calls the
     * simulation pane builder to create layout and model.nqueen objects.
     */

    @Override
    public Pane createRootPane() {
        BorderPane root = new BorderPane();
        StackPane stateView = new StackPane();
        stateViewCtrl = new NQueensViewCtrl(stateView);
        List<Parameter> params = createParameters();
        TaskExecutionPaneBuilder builder = new TaskExecutionPaneBuilder();
        builder.defineParameters(params);
        builder.defineStateView(stateView);
        builder.defineInitMethod(this::initialize);
        builder.defineTaskMethod(this::startExperiment);
        taskPaneCtrl = builder.getResultFor(root);
        taskPaneCtrl.setParam(TaskExecutionPaneCtrl.PARAM_EXEC_SPEED, 0);
        return root;
    }

    protected List<Parameter> createParameters() {
        Parameter p1 = new Parameter(PARAM_STRATEGY, "BT","FC", "AC3-FC", "MAC-3", "FC-MRV","FC-LCV");
        Object[] arr = new Object[97];
        for (int i = 0; i < 97; i++) {
            arr[i] = i + 4;
        }
        Parameter p2 = new Parameter(PARAM_BOARD_SIZE, arr);
        p2.setDefaultValueIndex(0);

        Parameter p3 = new Parameter(SOLUTION,"Single","All");
        Parameter p4 = new Parameter(POSITION,"Static","Specific");
        return Arrays.asList(p1, p2,p3,p4);
    }


    /**
     * Displays the initialized board on the state model.nqueen.view.
     */
    @Override
    public void initialize() {

        csp = new NQueensCSP(taskPaneCtrl.getParamAsInt(PARAM_BOARD_SIZE));

        Object strategy = taskPaneCtrl.getParamValue(PARAM_STRATEGY);
        algorithmName=(String)strategy;

        if (strategy.equals("BT")) {
            bSolver=new FlexibleBacktrackingSolver<>();
            algorithmName="BT";
        }
        else if(strategy.equals("FC"))
        {
            bSolver=new FlexibleBacktrackingSolver<>();
            bSolver.set(new ForwardCheckingStrategy<>());
            algorithmName="FC";
        }else if(strategy.equals("AC3-FC"))
        {
            bSolver=new FlexibleBacktrackingSolver<>();
            bSolver.set(new AC3Strategy<>()).set(new ForwardCheckingStrategy<>());
            algorithmName="AC3-FC";
        }else if(strategy.equals("MAC-3"))
        {
            bSolver=new FlexibleBacktrackingSolver<>();
            bSolver.set(new AC3Strategy<>());
            algorithmName="MAC-3";
        }else if(strategy.equals("FC-MRV"))
        {
            bSolver=new FlexibleBacktrackingSolver<>();
            bSolver.set(new ForwardCheckingStrategy<>()).set(CspHeuristics.mrv());
            algorithmName="FC-MRV";
        }else if(strategy.equals("FC-LCV"))
        {
            bSolver=new FlexibleBacktrackingSolver<>();
            bSolver.set(new ForwardCheckingStrategy<>()).set(CspHeuristics.lcv());
            algorithmName="FC-LCV";
        }
        solver=bSolver;
        solver.addCspListener(stepCounter);
        solver.addCspListener(
                (csp, assign, var) -> {
                    if (assign != null)
                    {updateStateView(getBoard(assign));
                     taskPaneCtrl.setText("Assignment Evolved ="+assign.toString());
                     }
                    });
        stepCounter.reset();
        Object value = taskPaneCtrl.getParamValue(POSITION);
        if(value.equals("Static")) {
            Util.setposition(false);
            stateViewCtrl.update(new NQueensBoard(csp.getVariables().size()));// For initial update
        }else {
            Util.setposition(true);
            String y= JOptionPane.showInputDialog("Enter the y coordinate");
            String x= JOptionPane.showInputDialog("Enter the x coordinate");
            NQueensBoard.y=Integer.parseInt(y)-1;
            NQueensBoard.x=Integer.parseInt(x)-1;
            stateViewCtrl.update(new NQueensBoard(csp.getVariables().size(), NQueensBoard.Config.Queen_IN_SPECIFIC));// For initial update
        }


        taskPaneCtrl.setStatus("");
        taskPaneCtrl.textArea.clear();
        bSolver.clearAll();
    }

    @Override
    public void finalize() {
        taskPaneCtrl.cancelExecution();

    }


    /**
     * Starts the experiment.
     */
    public void startExperiment() {
        StringBuilder stringBuilder=new StringBuilder();
        taskPaneCtrl.setText("<Simulation-Log>");
        taskPaneCtrl.setText("................................");
        Object choice = taskPaneCtrl.getParamValue(SOLUTION);
        Optional<Assignment<Variable, Integer>> solution;
        if(choice.equals("Single")) {

            if (taskPaneCtrl.getParamValue(POSITION).equals("Specific"))
            {
                /*Assignment expected = new Assignment();
                Variable variable= new Variable("Q" + (NQueensBoard.y+1));
                expected.add(variable, (NQueensBoard.x+1));
                solution= bSolver.solveSpecific(csp,expected);*/

                solution=bSolver.solve(csp);
            }else
            {
                solution=bSolver.solve(csp);
            }
        }
        else {
            solution=bSolver.solveAll(csp);
        }

        if (solution.isPresent()) {//?
            NQueensBoard board = getBoard(solution.get());
            stateViewCtrl.update(board);
        }

        taskPaneCtrl.setText("................................");
        taskPaneCtrl.setText("</Simulation-Log>\n");

        taskPaneCtrl.setText("Time to solve in second \t\t\t= "+bSolver.getTime() + " s");
        stringBuilder.append("Algorithm Name \t\t=  " + algorithmName + "\n");
        stringBuilder.append("Time to solve in second       \t \t = " + bSolver.getTime() + " s"+ "\n");

        taskPaneCtrl.setText("Number of nodes visited\t\t\t= " + bSolver.getNumberOfNodesVisited() + " nodes");
        stringBuilder.append("Number of nodes visited       \t = " + bSolver.getNumberOfNodesVisited() + " nodes"+ "\n");
        if(!choice.equals("Single")) {
            taskPaneCtrl.setText("Number of Solutions\t\t\t= " + bSolver.getNumberOfSolution() + " solutions");
            stringBuilder.append("Number of Solutions\t      \t = " + bSolver.getNumberOfSolution() + " solutions"+ "\n");
        }
        storeResult.addResult(stringBuilder.toString());
    }


    private NQueensBoard getBoard(Assignment<Variable, Integer> assignment) {
        NQueensBoard board = new NQueensBoard(csp.getVariables().size());
        for (Variable var : assignment.getVariables()) {
            int col = Integer.parseInt(var.getName().substring(1)) - 1;
            int row = assignment.getValue(var) - 1;
            board.addQueenAt(new XYLocation(col, row));
        }
        return board;
    }

    /**
     * Caution: While the background thread should be slowed down, updates of
     * the GUI have to be done in the GUI thread!
     */

    private void updateStateView(NQueensBoard board) {

        Platform.runLater(() -> {
            //if you need to update a GUI component from a non-GUI thread,you can use that to put your update in a queue
            stateViewCtrl.update(board);
            taskPaneCtrl.setStatus(stepCounter.getResults().toString());
        });
        taskPaneCtrl.waitAfterStep();
    }
}
