import Backjumping.csp.Problem;
import Backjumping.prosser.Bcssp;
import Backjumping.prosser.CBJ;
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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Integrable application which demonstrates how different CSP solution
 * strategies solve the N-Queens problem.
 **/

public class BJCspApp extends IntegrableApplication {

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
    CBJ a;
    public static String size;
    NQueensBoard board;

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
    public Pane createRootPane()throws Exception {
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

        Parameter p1 = new Parameter(PARAM_STRATEGY, "Choose Algorithms","BT","BJ", "FC", "AC3-FC", "MAC-3", "FC-MRV","FC-LCV");
        Object[] arr = new Object[97];
        for (int i = 0; i < 97; i++) {
            arr[i] = i + 4;
        }

        Parameter p2 = new Parameter(PARAM_BOARD_SIZE, arr);
        p2.setDefaultValueIndex(0);

        Parameter p3 = new Parameter(SOLUTION,"Single","All");
        return Arrays.asList(p1, p2,p3);
    }

    /**
     * Displays the initialized board on the state model.nqueen.view.
     */
    @Override
    public void initialize() {

        stateViewCtrl.update(new NQueensBoard(taskPaneCtrl.getParamAsInt(PARAM_BOARD_SIZE), NQueensBoard.Config.EMPTY));

        updateStateView(getBoard());//Board Size gives static pic.

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
        CBJ a=new CBJ(new Problem(taskPaneCtrl.getParamAsInt(PARAM_BOARD_SIZE)));
        a.bcssp();
        a.printV(System.out);
        Object choice = taskPaneCtrl.getParamValue(SOLUTION);


        if(choice.equals("Single")) {

        }
        else {
            board=new NQueensBoard(taskPaneCtrl.getParamAsInt(PARAM_BOARD_SIZE), NQueensBoard.Config.EMPTY);
        }

        taskPaneCtrl.setText(a.get());
        taskPaneCtrl.setText("................................");
        taskPaneCtrl.setText("</Simulation-Log>\n");
        double end = System.currentTimeMillis();

        bSolver.clearAll();
       // board.clear();
    }

    /*private NQueensBoard getBoard() {
        int size=taskPaneCtrl.getParamAsInt(PARAM_BOARD_SIZE);
        NQueensBoard board = new NQueensBoard(size);
        ArrayList<Integer> arr=new ArrayList<>();
        for (int index=0;index<size;index++) {
            String st=CBJ.a.get(CBJ.a.size()-(index+1));
            String[] a =st.split(" ");
            int col=Integer.parseInt(a[0]);
            int row=Integer.parseInt(a[1]);
            board.addQueenAt(new XYLocation(col, row));
           // board.removeQueenFrom(new XYLocation(col,row));
        }
        return board;
    }*/

    private NQueensBoard getBoard() {
        int size=taskPaneCtrl.getParamAsInt(PARAM_BOARD_SIZE);
        NQueensBoard board = new NQueensBoard(size, NQueensBoard.Config.EMPTY);

        for (int index = 0; index< size; index++) {
            for(int l=0;l<size;l++)
                board.removeQueenFrom(new XYLocation(index, l));
        }
        for (int index = 0; index< Bcssp.aa.size(); index++) {
            String st=Bcssp.aa.get(index).toString();
            String[] a =st.split(" ");
            int col=Integer.parseInt(a[0]);
            int row=Integer.parseInt(a[1]);
            board.addQueenAt(new XYLocation(col, row));
        }
        return board;
    }

      /*private NQueensBoard removeBoard() {
        int size=taskPaneCtrl.getParamAsInt(PARAM_BOARD_SIZE);
        NQueensBoard board = new NQueensBoard(size, NQueensBoard.Config.EMPTY);

        for (int index = 0; index< size; index++) {
            for(int l=0;l<size;l++)
            board.removeQueenFrom(new XYLocation(index, l));
        }
        return board;
    }*/

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
