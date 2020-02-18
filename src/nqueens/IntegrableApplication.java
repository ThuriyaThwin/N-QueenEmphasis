package nqueens;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Abstract super class for integrable applications. Applications of this kind
 * consist of a title, a root pane, and methods for initialization and finalization.
 * They inherit a start method which cannot be changed. This makes them integrable
 * into a common main window.
 */

public abstract class IntegrableApplication extends Application {

    protected double defaultWidth = 1200;
    @Override
    public final void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(getTitle());
        initialize();
        primaryStage.setScene(new Scene(createRootPane(), defaultWidth, defaultHeight));//opt for the size of the scene by passing its dimensions (height and width) along with the root node to its constructor.
        //primaryStage.setResizable(false);
        primaryStage.show();//must call to display the contents of a stage
    }

    protected double defaultHeight = 800;

    /**
     * Returns the title of the application.
     */
    public abstract String getTitle();

    /**
     * Creates the pane to be used as root of the application's scene.
     */

    public abstract Pane createRootPane() throws Exception;

    /**
     * Defines initialization steps (scene graph has been created and made visible on screen before).
     */

    public abstract void initialize();

    /**
     * Finalization steps to be performed when switching between applications (needed especially in case
     * several applications shall be integrated into a common window).
     */
    public abstract void finalize();
}
