package ca.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ca.system.CALogReduct;
import ca.system.CATask;
import ca.view.CAFileChooser;
import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CAApplication extends Application{
	
	public static final String APP_NAME = "CheColyzer";
	public static final int MAX_MEMBER = 3;

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
		ProgressIndicator indicator = new ProgressIndicator();

		fileSelectButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				CAFileChooser fc = new CAFileChooser();
				List<File> files = new ArrayList<File>();
				files = fc.open();
				if (files.size() > 0 && files.size() <= MAX_MEMBER) {
					indicator.setVisible(true);
					
					CATask caTask = new CATask(files);
					
					caTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

						@Override
						public void handle(WorkerStateEvent arg0) {
							List<CALogReduct> lrs = new ArrayList<CALogReduct>();
							indicator.setVisible(false);
							lrs = caTask.getLrs();
							for (CALogReduct lr : lrs) {
								System.out.println("User Name       : " + lr.getUser());
								System.out.println("Total Login     : " + lr.getTotalLoginTime() + " minutes");
								System.out.println("All Import      : " + lr.getAllImportCount() + " times");
								System.out.println("Java Import     : " + lr.getJavaImportCount() + " times");
								System.out.println("Resource Import : " + lr.getMaterialImportCount() + " times");
							}
						}
					});
					
					Executor executor = Executors.newSingleThreadExecutor();
					executor.execute(caTask);
				}
			}
		});
		
		vbox.getChildren().addAll(fileSelectButton, indicator);
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
