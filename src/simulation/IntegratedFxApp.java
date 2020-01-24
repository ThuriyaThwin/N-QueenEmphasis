package simulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class IntegratedFxApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static void defineContent(IntegratedAppPaneBuilder builder) {
        builder.registerApp(Backtrack.class);
        builder.registerApp(Backjump.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        IntegratedAppPaneBuilder builder = new IntegratedAppPaneBuilder();
        builder.defineTitle("Constraint Solver ");
        defineContent(builder);
        BorderPane root = new BorderPane();
        builder.getResultFor(root, primaryStage);
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }
}