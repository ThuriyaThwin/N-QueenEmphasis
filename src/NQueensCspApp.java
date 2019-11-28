import Backjumping.prosser.CBJ;
import engine.algo.AbstractBacktrackingSolver;
import javafx.application.Application;
import javafx.stage.Stage;
import model.nqueen.IntegrableApplication;
import model.nqueen.NQueensViewCtrl;
import model.nqueen.TaskExecutionPaneBuilder;
import model.nqueen.TaskExecutionPaneCtrl;
import engine.algo.CspHeuristics;
import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.*;
import engine.csp.inference.AC3Strategy;
import engine.csp.inference.ForwardCheckingStrategy;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.nqueen.NQueensCSP;
import util.StoreResult;
import util.XYLocation;
import model.nqueen.view.NQueensBoard;
import model.nqueen.view.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Integrable application which demonstrates how different CSP solution
 * strategies solve the N-Queens problem.
 **/

public class NQueensCspApp extends IntegrableApplication {

    public static void main(String[] args) {
        launch(args);
    }

    private final static String SOLUTION="solution =";
    private final static String PARAM_STRATEGY = "";
    private final static String PARAM_BOARD_SIZE = "b = ";

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
        return "N-Queens CSP App";
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
        Parameter p1 = new Parameter(PARAM_STRATEGY, "Choose Algorithms","BT","BJ","FC", "AC3-FC", "MAC-3", "FC-MRV","FC-LCV");
        Object[] arr = new Object[97];
        for (int i = 0; i < 97; i++) {
            arr[i] = i + 4;
        }
        Parameter p2 = new Parameter(PARAM_BOARD_SIZE, arr);
        p2.setDefaultValueIndex(0);

        Parameter p3 = new Parameter(SOLUTION,"Single","All");

        return Arrays.asList(p1, p2,p3);
    }

    public void runAnotherApp(Class<? extends Application> BJCspApp) throws Exception {
        Application app2 = BJCspApp.newInstance();
        Stage anotherStage = new Stage();
        app2.start(anotherStage);
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
        }else if(strategy.equals("BJ")){
            try {
                runAnotherApp(BJCspApp.class);
            }catch (Exception e)
            {

            }

        }
        else if(strategy.equals("FC"))
        {
            bSolver=new FlexibleBacktrackingSolver<>();
            bSolver.set(new ForwardCheckingStrategy<>());
        }else if(strategy.equals("AC3-FC"))
        {
            bSolver=new FlexibleBacktrackingSolver<>();
            bSolver.set(new AC3Strategy<>()).set(new ForwardCheckingStrategy<>());
        }else if(strategy.equals("MAC-3"))
        {
            bSolver=new FlexibleBacktrackingSolver<>();
            bSolver.set(new AC3Strategy<>());
        }else if(strategy.equals("FC-MRV"))
        {
            bSolver=new FlexibleBacktrackingSolver<>();
            bSolver.set(new ForwardCheckingStrategy<>()).set(CspHeuristics.mrv());
        }else if(strategy.equals("FC-LCV"))
        {
            bSolver=new FlexibleBacktrackingSolver<>();
            bSolver.set(new ForwardCheckingStrategy<>()).set(CspHeuristics.lcv());
        }

        solver=bSolver;
        solver.addCspListener(stepCounter);
        solver.addCspListener((csp, assign, var) -> {
            if (assign.getVariables().size()!=0) {
                updateStateView(getBoard(assign));
                taskPaneCtrl.setText("Assignment evolved: "+assign.toString());
            }else{
                taskPaneCtrl.setText("       CSP evolved:  ***");
            }
        });
        stepCounter.reset();
        stateViewCtrl.update(new NQueensBoard(csp.getVariables().size()));
        taskPaneCtrl.setStatus("");
        taskPaneCtrl.textArea.clear();
        bSolver.clearAll();
    }

    @Override
    public void finalize() {

    }


    /**
     * Starts the experiment.
     */
    public void startExperiment() {
        double start = System.currentTimeMillis();
        StringBuilder stringBuilder=new StringBuilder();
        taskPaneCtrl.setText("<Simulation-Log>");
        taskPaneCtrl.setText("................................");
        Object choice = taskPaneCtrl.getParamValue(SOLUTION);
        Optional<Assignment<Variable, Integer>> solution;
        if(choice.equals("Single")) {
            solution =bSolver.solve(csp);
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
        double end = System.currentTimeMillis();
        stringBuilder.append("Algorithm Name \t\t\t"+algorithmName+ "\n");

        if(choice.equals("All")) {
            taskPaneCtrl.setText("The number of solution \t\t\t=" + bSolver.getNumberOfSolution());
            stringBuilder.append("The number of solution =" + bSolver.getNumberOfSolution()+"\n");
        }

        taskPaneCtrl.setText("Time to solve in second \t\t\t= " + (end - start) * 0.001 + " s");
        stringBuilder.append("Time to solve in second       \t \t = " + (end - start) * 0.001 + " s"+ "\n");
        taskPaneCtrl.setText("Number of backtracking occurs \t= " + bSolver.getNumberOfBacktrack() + " times");
        stringBuilder.append("Number of backtracking occurs  \t = " + bSolver.getNumberOfBacktrack() + " times"+ "\n");
        taskPaneCtrl.setText("Number of nodes visited\t\t\t= " + bSolver.getNumberOfNodesVisited() + " nodes");
        stringBuilder.append("Number of nodes visited        \t\t = " + bSolver.getNumberOfNodesVisited() + " nodes"+ "\n");
        taskPaneCtrl.setText("Number of nodes assigned\t\t= " + bSolver.getNumberOfNodesAssigned() + " nodes");
        stringBuilder.append("Number of nodes assigned          \t = " + bSolver.getNumberOfNodesAssigned() + " nodes"+ "\n");
        storeResult=new StoreResult(stringBuilder.toString());
        bSolver.clearAll();
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
        Platform.runLater(() -> { //if you need to update a GUI component from a non-GUI thread,you can use that to put your update in a queue
            stateViewCtrl.update(board);
            taskPaneCtrl.setStatus(stepCounter.getResults().toString());
        });
        taskPaneCtrl.waitAfterStep();
    }
}
