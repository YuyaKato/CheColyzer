package ca.app;

import java.io.File;

import ca.view.CAFileChooser;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CAApplication extends Application{
	
	public static final String APP_NAME = "CheColyzer";

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		initialize(primaryStage);
		open(primaryStage);
	}

	public void initialize(Stage primaryStage) {
		
		Button fileSelectButton = new Button("File select");
		VBox vbox = new VBox();
		StackPane root = new StackPane();
		Scene scene;
		
		fileSelectButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				CAFileChooser fc = new CAFileChooser();
				File file = fc.open();
				if (file != null) {
					readFile(file);
				}
			}
		});
		
		vbox.getChildren().addAll(fileSelectButton);
		root.getChildren().addAll(vbox);
		scene = new Scene(root, 300, 250);
		primaryStage.setTitle(APP_NAME);
		primaryStage.setScene(scene);
	}
	
	public void open(Stage primaryStage) {
		primaryStage.show();
	}
	
	public void readFile(File file) {
		
	}
}
