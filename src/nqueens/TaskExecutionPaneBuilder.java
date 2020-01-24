package nqueens;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import nqueens.view.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Builder class for task execution panes. To add suitable graphical elements
 * to a given pane and obtain a corresponding model.nqueen class, just create a builder,
 * call the define methods to specify what you need, and then get the result with
 * {@link #getResultFor}.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class TaskExecutionPaneBuilder {

    public TextField boardsize;
    protected List<Parameter> parameters = new ArrayList<>();
    protected Optional<Node> stateView = Optional.empty();
    /**
     * Should return true if initialization was successful.
     */
    protected Optional<Runnable> initMethod = Optional.empty();
    protected Optional<Runnable> taskMethod = Optional.empty();

    public final void defineParameters(List<Parameter> params) {
        parameters.clear();
        parameters.addAll(params);
    }

    public final void defineStateView(Node stateView) {
        this.stateView = Optional.of(stateView);
    }

    public final void defineInitMethod(Runnable initMethod) {
        this.initMethod = Optional.of(initMethod);
    }

    public final void defineTaskMethod(Runnable taksMethod) {
        this.taskMethod = Optional.of(taksMethod);
    }

    /**
     * Adds a toolbar, a state model.nqueen.view, and a status label to the provided pane and returns
     * a model.nqueen class instance. The toolbar contains combo boxes to control parameter settings
     * and buttons for task execution control. The model.nqueen class instance handles user events and provides
     * access to user settings (parameter settings, execution speed, status text, ...).
     */
    public TaskExecutionPaneCtrl getResultFor(BorderPane pane) {
        List<ComboBox<String>> combos = new ArrayList<>();
        parameters.add(createExecutionSpeedParam());//Can add parameters

        for (Parameter param : parameters) {
            ComboBox<String> combo = new ComboBox<>();
            combo.setId(param.getName());
            if (combo.getId().equals("b = ")) {
                combo.setVisible(false);
                combo.setMaxSize(0, 0);
            }
            if (combo.getId().equals("")) {

            }
            combo.getItems().addAll(param.getValueNames());
            combo.getSelectionModel().select(param.getDefaultValueIndex());
            combos.add(combo);
        }

        Button executeBtn = new Button();
        Node[] tools = new Node[combos.size() + 4];
        for (int i = 0; i < combos.size() - 1; i++)
            tools[i] = combos.get(i);
        tools[combos.size() - 1] = combos.get(combos.size() - 1);
        tools[combos.size() + 0] = executeBtn;
        tools[combos.size() + 1] = new Separator();
        boardsize = new TextField();
        boardsize.setPromptText("Enter Size:");
        boardsize.setMaxSize(100, 10);
        tools[combos.size() + 2] = boardsize;
        Button store = new Button("Compare Result");
        tools[combos.size() + 3] = store;
        ToolBar toolBar = new ToolBar(tools);
        Label statusLabel = new Label();
        statusLabel.setMaxWidth(Double.MAX_VALUE);
        statusLabel.setAlignment(Pos.CENTER);
        statusLabel.setFont(Font.font(16));
        SplitPane splitPane = new SplitPane();
        TextArea textArea = new TextArea();
        textArea.setFont(Font.font("Verdana", 19));
        splitPane.getItems().add(textArea);
        pane.setRight(splitPane);
        pane.setTop(toolBar);
        if (stateView.isPresent()) {
            if (stateView.get() instanceof Canvas) {
                // make canvas resizable
                Canvas canvas = (Canvas) stateView.get();
                Pane canvasPane = new Pane();
                canvasPane.getChildren().add(canvas);
                canvas.widthProperty().bind(canvasPane.widthProperty());
                canvas.heightProperty().bind(canvasPane.heightProperty());
                pane.setCenter(canvasPane);
                pane.setStyle("-fx-background-color: white");
            } else
                pane.setCenter(stateView.get());
        }

        pane.setBottom(statusLabel);

        if (!initMethod.isPresent())
            throw new IllegalStateException("No initialization method defined.");
        if (!taskMethod.isPresent())
            throw new IllegalStateException("No task method defined.");

        return new TaskExecutionPaneCtrl(parameters, combos, initMethod.get(), taskMethod.get(),
                executeBtn, statusLabel, textArea, boardsize, store);
    }

    /**
     * Factory method defining the execution speed options. Value
     * <code>Integer.MAX_VALUE</code> is used for step mode.
     */

    protected Parameter createExecutionSpeedParam() {
        Parameter result = new Parameter(TaskExecutionPaneCtrl.PARAM_EXEC_SPEED, 1, 100, 400, 800, Integer.MAX_VALUE);
        result.setValueNames("VeryFast", "Fast", "Medium", "Slow", "Pause");
        //result.setDefaultValueIndex(0);
        return result;
    }
}
