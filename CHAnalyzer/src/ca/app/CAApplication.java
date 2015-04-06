package ca.app;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ca.system.CALogReduct;
import ca.view.CAFileChooser;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CAApplication extends Application{
	
	public static final String APP_NAME = "CheColyzer";
	
	private CALogReduct lr = new CALogReduct();

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
		Label tltLabel = new Label();
		Label aicLabel = new Label();
		Label jicLabel = new Label();
		Label micLabel = new Label();
		ProgressIndicator indicator = new ProgressIndicator();

		fileSelectButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				CAFileChooser fc = new CAFileChooser();
				File file = fc.open();
				if (file != null) {
					indicator.setVisible(true);
					Task<Void> task = new Task<Void>() {

						@Override
						protected Void call() throws Exception {
							lr = reduct(file);
							return null;
						}
					};
					task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

						@Override
						public void handle(WorkerStateEvent event) {
							indicator.setVisible(false);
							tltLabel.setText("Total Login     : " + lr.getTotalLoginTime() + " minutes");
							aicLabel.setText("All Import      : " + lr.getAllImportCount() + " times");
							jicLabel.setText("Java Import     : " + lr.getJavaImportCount() + " times");
							micLabel.setText("Resource Import : " + lr.getMaterialImportCount() + " times");
						}
					});
					
					Executor executor = Executors.newSingleThreadExecutor();
					executor.execute(task);
				}
			}
		});
		
		vbox.getChildren().addAll(fileSelectButton, indicator, tltLabel, aicLabel, jicLabel, micLabel);
		root.getChildren().addAll(vbox);
		scene = new Scene(root, 300, 250);
		primaryStage.setTitle(APP_NAME);
		primaryStage.setScene(scene);
		indicator.setVisible(false);
	}
	
	public CALogReduct reduct(File file) {
		CALogReduct lr = new CALogReduct();
		lr.reduct(lr.loadCSV(file));
		return lr;
	}
	
	public void open(Stage primaryStage) {
		primaryStage.show();
	}
}
