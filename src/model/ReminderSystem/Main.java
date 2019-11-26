package model.ReminderSystem;


import engine.algo.CspHeuristics;
import engine.algo.FlexibleBacktrackingSolver;
import engine.csp.Assignment;
import engine.csp.CSP;
import engine.csp.inference.ForwardCheckingStrategy;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Main extends Application {
    int WINDOW_WIDTH = 750;
    int WINDOW_HEIGHT = 400;
    int FORM_WIDTH = 75;
    TextField[] nameFields;
    TextField[] prefFields;
    List<Object[]> domains;
    String[] variables;
    int amount;
    Group root;


    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new Group();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.CRIMSON);
        primaryStage.setTitle("Amount of person");

        //Block for POPUP
        Label label1= new Label("Enter amount of person(s)");

        TextField inputField = new TextField();

        EventHandler<ActionEvent> eventReadInput = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                amount = Integer.parseInt(inputField.getText());
                 if( amount>10) {
                     System.out.println("The Maximum Limit Reached!");
                     System.exit(0);
                 }
                primaryStage.setScene(scene);
                primaryStage.setTitle("RE-SYST : Reminder System");
                createMainScene(amount);
            }
        };


        Button readInput = new Button("This amount is ok");
        readInput.setOnAction(eventReadInput);

        VBox layout= new VBox(10);

        layout.getChildren().addAll(label1, inputField,readInput);

        layout.setAlignment(Pos.CENTER);

        Scene popupScene= new Scene(layout, 300, 250);

        //Still block for POPUP

        primaryStage.setScene(popupScene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private GridPane createRectangle(GridPane gridpane, int amountOfCard) {
        this.nameFields = new TextField[amountOfCard];
        this.prefFields = new TextField[amountOfCard];
        for (int i = 0; i < amountOfCard; i++) {
            Rectangle square = new Rectangle((WINDOW_WIDTH/5), (WINDOW_HEIGHT/2)-20);
            square.setStroke(Color.BLACK);
            square.fillProperty().set(Color.ANTIQUEWHITE);

            GridPane smallgrid = new GridPane();
            Label nameLabel = new Label("Name:");
            smallgrid.add(nameLabel, 0, 1);

            TextField nameTextField = new TextField();
            nameTextField.setPrefWidth(FORM_WIDTH);
            smallgrid.add(nameTextField, 1, 1);

            Label prefLabel = new Label("Preference:");
            smallgrid.add(prefLabel, 0, 2);

            TextField prefField = new TextField();
            prefField.setPrefWidth(FORM_WIDTH);
            smallgrid.add(prefField, 1, 2);

            nameFields[i] = nameTextField;
            prefFields[i] = prefField;

//            System.out.println("This point on square creator");
            if (i > 5) {
                gridpane.add(square, i-5,1);
                gridpane.add(smallgrid, i-5, 1);
            } else {
                gridpane.add(square, i, 0);
                gridpane.add(smallgrid, i ,0);
            }
        }
        System.out.println("Was on square creator");
        return gridpane;
    }

    public void createMainScene(int amount){
        GridPane gridpane = new GridPane();
        gridpane.setGridLinesVisible(true);
        Button processAllButton = new Button ("Get Assignment");
        EventHandler<ActionEvent> processAll = createCSPProcessor();
        processAllButton.setOnAction(processAll);
        gridpane.add(processAllButton, 4,2);
        System.out.println("Was on main creator");
        root.getChildren().add(createRectangle(gridpane, amount));
    }

    public EventHandler<ActionEvent> createCSPProcessor(){
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                domains = new ArrayList<>();
                variables = new String[nameFields.length];
                for (int index = 0; index < nameFields.length; index++) {
                    String name = nameFields[index].getText();
                    String pref = prefFields[index].getText();
                    if (!name.isEmpty() && !pref.isEmpty()) {
                        System.out.println("Name :" + name);
                        System.out.println("Preference : " + pref);
                        variables[index] = name;
                    }
                }

                for (int index = 0; index < prefFields.length; index++) {
                    domains.add(prefFields[index].getText().split(" "));
                }

                CSP csp = new ReminderCSP(variables, domains);
                FlexibleBacktrackingSolver bts=new FlexibleBacktrackingSolver();
                bts.set(new ForwardCheckingStrategy()).set(CspHeuristics.mrv());
                Optional<Assignment> results = bts.solve(csp);
                createPopup(results.get().toString());
            }
        };
    }
	
	public void createPopup(String message){
		Stage popupwindow=new Stage();
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Result");
		Label label1= new Label(message.replace("="," reminds "));
		
		Button button1= new Button("OK");
		button1.setOnAction(e -> popupwindow.close());	 

		VBox layout= new VBox(10);
			 
		layout.getChildren().addAll(label1, button1);
			  
		layout.setAlignment(Pos.CENTER);
			  
		Scene scene1= new Scene(layout, 900, 200);
			  
		popupwindow.setScene(scene1);
			  
		popupwindow.showAndWait();
		   
	}

}

