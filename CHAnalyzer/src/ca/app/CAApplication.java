package ca.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ca.system.CAResult;
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

	List<File> lFiles = new ArrayList<File>();
	File qFile;
	
	public void initialize(Stage primaryStage) {
		
		Button lFileSelectButton = new Button("Log File");
		Button qFileSelectButton = new Button("Questionnaire File");
		Button analyzeButton = new Button("Analyze");
		VBox vbox = new VBox();
		StackPane root = new StackPane();
		Scene scene;
		ProgressIndicator indicator = new ProgressIndicator();
		CAFileChooser fc = new CAFileChooser();
		
		lFileSelectButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO 拡張子チェック
				lFiles = fc.openMultiple();
				for (File lFile : lFiles) {
					System.out.println("Log File : " + lFile.getName());
				}
			}
		});
		
		qFileSelectButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO 拡張子チェック
				qFile = fc.openSingle();
				System.out.println("Questionnaire File : " + qFile.getName());
			}
		});

		analyzeButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {	
				
				if (lFiles.size() > 0 && lFiles.size() <= MAX_MEMBER && qFile != null) {
					indicator.setVisible(true);
					
					CATask caTask = new CATask(lFiles, qFile);
					
					caTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

						@Override
						public void handle(WorkerStateEvent arg0) {
							List<CAResult> results = new ArrayList<CAResult>();
							indicator.setVisible(false);
							results = caTask.getResults();
							for (CAResult result : results) {
								System.out.println("User Name        : " + result.getLr().getUser());
								System.out.println("Total Login      : " + result.getLr().getTotalLoginTime() + " minutes");
								System.out.println("All Import       : " + result.getLr().getAllImportCount() + " times");
								System.out.println("Java Import      : " + result.getLr().getJavaImportCount() + " times");
								System.out.println("Resource Import  : " + result.getLr().getMaterialImportCount() + " times");
								System.out.println("Skill Rank       : " + result.getQr().getSkillRank());
								System.out.println("Description Rate : " + result.getQr().getDescriptionRate() + " %");
							}
						}
					});
					
					Executor executor = Executors.newSingleThreadExecutor();
					executor.execute(caTask);
				}
			}
		});
		
		vbox.getChildren().addAll(lFileSelectButton, qFileSelectButton, analyzeButton, indicator);
		root.getChildren().addAll(vbox);
		scene = new Scene(root, 300, 250);
		primaryStage.setTitle(APP_NAME);
		primaryStage.setScene(scene);
		indicator.setVisible(false);
	}
	
	public void open(Stage primaryStage) {
		primaryStage.show();
	}
}
