package simulation;

import console.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class IntegratedFxApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private void registerContent(IntegratedAppPaneBuilder builder)
    {
        builder.registerApp(Backtrack.class);
        builder.registerApp(Backjump.class);
        builder.registerDemo(BTConsole.class);
        builder.registerDemo(BJConsole.class);
        builder.registerDemo(FCConsole.class);
        builder.registerDemo(AC3Console.class);
        builder.registerDemo(MAC3Console.class);
        builder.registerDemo(FCMRVConsole.class);
        builder.registerDemo(FCLCVConsole.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        IntegratedAppPaneBuilder builder = new IntegratedAppPaneBuilder();
        builder.defineTitle("Constraint Solver ");
        registerContent(builder);
        BorderPane root = new BorderPane();
        builder.getResultFor(root, primaryStage);
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }
}