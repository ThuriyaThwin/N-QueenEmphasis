package model.nqueen;


import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import model.nqueen.view.Parameter;
import model.nqueen.view.Test;
import util.CancellableThread;
import util.StoreResult;
import util.Tasks;

import javax.swing.*;
import java.util.List;

/**
 * Controller class for task execution panes. It is responsible for maintaining the
 * current parameter settings, the task execution state, the current execution
 * thread, and for handling events (parameter change events and execute button events).
 * If not otherwise stated, methods must be called from the FX Application Thread.
 * 
 */

public class TaskExecutionPaneCtrl {

	public enum State {
		READY, RUNNING, FINISHED, PAUSED, CANCELLED
	}

	public final static String PARAM_EXEC_SPEED = "executionSpeed";

	private Button executeBtn;
	private Label statusLabel;
	public TextField boardsize;
	public TextArea textArea;
	private List<Parameter> params;
	private List<ComboBox<String>> paramCombos;
	private Runnable initMethod;
	private Runnable taskMethod;
	private Button store;
	private ObjectProperty<State> state = new SimpleObjectProperty<>();
	private Thread backgroundThread;
	private StoreResult result;

	/** Should only be called by the SimulationPaneBuilder. */
	public TaskExecutionPaneCtrl(List<Parameter> params, List<ComboBox<String>> paramCombos, Runnable initMethod,
								 Runnable taskMethod, Button executeBtn, Label statusLabel, TextArea textArea, TextField number,Button store) {
		this.params = params;
		this.paramCombos = paramCombos;
		this.initMethod = initMethod;
		this.taskMethod = taskMethod;
		this.executeBtn = executeBtn;
		this.statusLabel = statusLabel;
		this.textArea = textArea;
		this.boardsize = number;
		this.store = store;
		store.setOnMouseClicked(ev->{
			Test a=new Test();
		});
		boardsize.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setParamValue("b = ",boardsize.getText());
				System.out.println(boardsize.getText());
				paramCombos.get(1).getSelectionModel().select(Integer.parseInt(boardsize.getText())-4);
				int size=Integer.parseInt(boardsize.getText());
				if(size<4 || size>100)
				{
					JOptionPane.showMessageDialog(new JFrame("Warning!"),"Please Enter Board size between 4 and 100");
				}
			}
		});
		ChangeListener<String> listener = (obs, o, n) -> onParamChanged();
		for (ComboBox<String> combo : paramCombos)
			// allow simSpeed adjustments during simulation (without
			// re-initialization)
			if (!combo.getId().equals(PARAM_EXEC_SPEED))
				combo.getSelectionModel().selectedItemProperty().addListener(listener);
		executeBtn.setOnAction(ev -> onExecuteButtonAction());
		// mouse-left on execute button toggles execution speed between StepMode and VeryFast
		executeBtn.setOnMouseClicked(ev -> {
			if (ev.getButton() == MouseButton.SECONDARY) {
				if (getParamAsInt(PARAM_EXEC_SPEED) == Integer.MAX_VALUE)
					setParamValue(PARAM_EXEC_SPEED, 20);
				else
					setParamValue(PARAM_EXEC_SPEED, Integer.MAX_VALUE);
			}
		});
		updateParamVisibility();
		state.addListener((obs, o, n) -> onStateChanged());
		setState(State.READY);
	}


	public int getParamValueIndex(String paramName) {
		int valIdx = -1;
		int paramIdx = Parameter.indexOf(params, paramName);
		if (paramIdx != -1)
			valIdx = paramCombos.get(paramIdx).getSelectionModel().getSelectedIndex();
		if (valIdx == -1)
			throw new IllegalStateException("No selected value for parameter " + paramName);
		return valIdx;
	}

	public Object getParamValue(String paramName) {
		int valIdx = getParamValueIndex(paramName);
		return Parameter.find(params, paramName).get().getValues().get(valIdx);	}

	public int getParamAsInt(String paramName) {
		return (Integer) getParamValue(paramName);
	}

	public void setParam(String paramName, int valueIdx) {
		int idx = Parameter.indexOf(params, paramName);
		if (idx != -1)
			paramCombos.get(idx).getSelectionModel().select(valueIdx);
		else
			throw new IllegalStateException("Parameter " + paramName + " not found.");
	}

	public void setParamValue(String paramName, Object value) {
		int pIdx = Parameter.indexOf(params, paramName);
		if (pIdx != -1) {
			List<Object> values = params.get(pIdx).getValues();
			for (int i = 0; i < values.size(); i++)
				if (values.get(i).equals(value)) {
					paramCombos.get(pIdx).getSelectionModel().select(i);
					break;
				}
		} else
			throw new IllegalStateException("Parameter " + paramName + " not found.");
	}

	/** Sets status label text (can be called from any thread). */
	public void setStatus(final String text) {
		if (Platform.isFxApplicationThread())
			statusLabel.setText(text);
		else
			Platform.runLater(() -> statusLabel.setText(text));
	}

	public void setText(final String text) {
		if (Platform.isFxApplicationThread())
			textArea.appendText(text + "\n");
		else
			Platform.runLater(() -> textArea.appendText(text + "\n"));
	}
	
	/**
	 * Sleeps as specified in simulation speed parameter. If the value is
	 * <code>Integer.MAX_VALUE</code> the simulation state is set to paused.
	 * Caller is typically not the FX Application Thread.
	 */
	public void waitAfterStep() {
		try {
			int msec = getParamAsInt(PARAM_EXEC_SPEED);
			if (msec == Integer.MAX_VALUE)
				setState(State.PAUSED);
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			// nothing to do here.
		}
	}

	/**
	 * Tries to stop task execution. This will only have an effect, if all loops in
	 * the running task check {@link CancellableThread#isCancelled()} in every time-consuming loop.
	 */
	public void cancelExecution() {
		if (backgroundThread != null && backgroundThread.isAlive()) {
			Tasks.cancel(backgroundThread);
			setState(State.CANCELLED);
			if (state.get() == State.PAUSED)
				backgroundThread.interrupt();
		}
	}

	/** Can be called from FX application threads and other threads as well. */
	private void setState(State newState) {
		if (Platform.isFxApplicationThread())
			state.set(newState);
		else
			Platform.runLater(() -> state.set(newState));
	}

	private void onParamChanged() {
		cancelExecution();
		setStatus("");
		updateParamVisibility();
		initMethod.run();
		setState(State.READY);
	}

	private void onStateChanged() {
		if (state.get() == State.READY)
			executeBtn.setText("Start");
		else if (state.get() == State.RUNNING)
			executeBtn.setText("Cancel");
		else if (state.get() == State.PAUSED)
			executeBtn.setText("Continue");
		else if (state.get() == State.CANCELLED)
			executeBtn.setText("Stop");
		else if (state.get() == State.FINISHED)
			executeBtn.setText("Init");

		boolean disable = state.get() != State.READY && state.get() != State.FINISHED;
		paramCombos.stream().filter(combo -> !combo.getId().equals(PARAM_EXEC_SPEED)).forEach
				(combo -> combo.setDisable(disable));
	}

	@SuppressWarnings("deprecation")
	private void onExecuteButtonAction() {
		if (state.get() == State.FINISHED) {
			onParamChanged();
		} else if (backgroundThread == null || !backgroundThread.isAlive()) {
			backgroundThread = Tasks.executeInBackground(this::runTask);
		} else if (state.get() == State.PAUSED) {
			backgroundThread.interrupt();
			setState(State.RUNNING);
		} else if (state.get() == State.CANCELLED) {
			backgroundThread.stop();
			setState(State.READY);
		} else {
			cancelExecution();
		}
	}

	private void runTask() {
		try {
			setState(State.RUNNING);
			taskMethod.run();
		} catch (Exception e) {
			String msg = e.getMessage();
			if (msg != null)
				setStatus(e.getClass().getSimpleName() + ": " + msg);
			else
				setStatus("Sorry, something went wrong during simulation: " + e.getClass().getSimpleName());
			e.printStackTrace();
		} catch (Error e) {
			setStatus("Sorry, something went totally wrong during simulation: " + e.getClass().getSimpleName());
			e.printStackTrace();
		}
		setState(State.FINISHED);
	}
	
	private void updateParamVisibility() {
		for (int i = 0; i < params.size(); i++) {
			String depParam = params.get(i).getDependencyParameter();
			if (depParam != null) {
				Parameter para = params.get(i);
				ComboBox<String> combo = paramCombos.get(i);
				combo.setVisible(para.getDependencyValues().contains(getParamValue(depParam)));
			}
		}
	}
}
