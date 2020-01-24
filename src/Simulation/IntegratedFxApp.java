package Simulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class IntegratedFxApp extends Application {

    public static void main(String[] args) {
        launch(args);
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

    public static void defineContent(IntegratedAppPaneBuilder builder) {
        builder.registerApp(BTCspApp.class);
        builder.registerApp(BJCspApp.class);
    }
}