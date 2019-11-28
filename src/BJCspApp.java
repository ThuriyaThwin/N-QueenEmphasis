import Backjumping.csp.Problem;
import Backjumping.prosser.Bcssp;
import Backjumping.prosser.CBJ;
import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Variable;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.nqueen.IntegrableApplication;
import model.nqueen.NQueensViewCtrl;
import model.nqueen.TaskExecutionPaneBuilder;
import model.nqueen.TaskExecutionPaneCtrl;
import model.nqueen.view.NQueensBoard;
import model.nqueen.view.Parameter;
import util.StoreResult;
import util.XYLocation;

import java.util.Arrays;
import java.util.List;

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
    FlexibleBacktrackingSolver<Variable, Integer> bSolver=new FlexibleBacktrackingSolver<>();
    public static String size;
    NQueensBoard board;
    public StoreResult storeResult=new StoreResult();


    @Override
    public String getTitle() {
        return "BJ CSP App";
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
        Parameter p1=new Parameter(PARAM_STRATEGY,"BJ");
        Object[] arr = new Object[97];
        for (int i = 0; i < 97; i++) {
            arr[i] = i + 4;
        }

        Parameter p2 = new Parameter(PARAM_BOARD_SIZE, arr);
        p2.setDefaultValueIndex(0);

        Parameter p3 = new Parameter(SOLUTION,"Single");

        return Arrays.asList(p1, p2,p3);
    }

    /**
     * Displays the initialized board on the state model.nqueen.view.
     */
    @Override
    public void initialize() {

        stateViewCtrl.update(new NQueensBoard(taskPaneCtrl.getParamAsInt(PARAM_BOARD_SIZE), NQueensBoard.Config.EMPTY));
       // updateStateView(getBoard());//Board Size gives static pic.
        taskPaneCtrl.setStatus("");
        taskPaneCtrl.textArea.clear();
        bSolver.clearAll();
        System.gc();
        Bcssp.aa.clear();

    }

    @Override
    public void finalize() {
        board.clear();
    }

     /**
     * Starts the experiment.
     */

    public void startExperiment() {

        double start = System.currentTimeMillis();
        board=new NQueensBoard(taskPaneCtrl.getParamAsInt(PARAM_BOARD_SIZE), NQueensBoard.Config.EMPTY);
        StringBuilder stringBuilder=new StringBuilder();
        CBJ a=new CBJ(new Problem(taskPaneCtrl.getParamAsInt(PARAM_BOARD_SIZE)));
        a.bcssp();
        String soulution=a.printV();
        NQueensBoard board=getBoard();
        taskPaneCtrl.setText("<Simulation-Log>\n");
        taskPaneCtrl.setText("................................");
        taskPaneCtrl.setText(a.get());
        taskPaneCtrl.setText("................................");
        taskPaneCtrl.setText("</Simulation-Log>\n");
        taskPaneCtrl.setText("The solution is :"+soulution+"\n");
        taskPaneCtrl.setText(board.getBoardPic());
        double end = System.currentTimeMillis();
        stringBuilder.append("Algorithm Name \t\t\t"+"BJ"+ "\n");
        taskPaneCtrl.setText("Time to solve in second \t\t\t= " + (end - start) * 0.001 + " s");
        stringBuilder.append("Time to solve in second       \t \t = " + (end - start) * 0.001 + " s"+ "\n");
        taskPaneCtrl.setText("Number of nodes visited\t\t\t= " + (Bcssp.assignments+1) + " nodes");
        stringBuilder.append("Number of nodes visited\t\t\t= " + (Bcssp.assignments+1) + " nodes"+"\n");
        storeResult=new StoreResult(stringBuilder.toString());
        bSolver.clearAll();
        bSolver.clearAll();
        Bcssp.aa.clear();
        System.gc();
        CBJ.arrayList.clear();
    }


    private NQueensBoard getBoard() {
        /*int size=taskPaneCtrl.getParamAsInt(PARAM_BOARD_SIZE);*/
        int size=board.getSize();
        NQueensBoard board = new NQueensBoard(size, NQueensBoard.Config.EMPTY);


        for (int index = 0; index< CBJ.arrayList.size(); index++) {
            String st=CBJ.arrayList.get(index).toString();
                String[] a = st.split(" ");
                int col = Integer.parseInt(a[0]);
                int row = Integer.parseInt(a[1]);
                    board.moveQueenTo(new XYLocation(col,row));
                   // board.addQueenAt(new XYLocation(col, row));
            updateStateView(board);
        }
        return board;
    }

    /*private NQueensBoard getBoard() {
        int size=taskPaneCtrl.getParamAsInt(PARAM_BOARD_SIZE);
        NQueensBoard board = new NQueensBoard(size, NQueensBoard.Config.EMPTY);


        for (int index = 0; index< CBJ.arrayList.size(); index++) {
            String st=CBJ.arrayList.get(index);
            String[] a =st.split(" ");
            int col=Integer.parseInt(a[0]);
            int row=Integer.parseInt(a[1]);
            board.addQueenAt(new XYLocation(col, row));
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
            taskPaneCtrl.setStatus("");
        });
        taskPaneCtrl.waitAfterStep();
    }
}
