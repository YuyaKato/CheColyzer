package ca.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ca.system.CAResult;
import ca.system.CATask;
import ca.view.CAFileChooser;
import ca.view.CATableView;
import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CAApplication extends Application{
	
	public static final String APP_NAME = "CheColyzer";
	public static final int MAX_MEMBER = 3;
	
	private Scene fileSelect = null;
	private CATableView resultTable = null;
	private List<File> lFiles = new ArrayList<File>();
	private File qFile;

	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle(APP_NAME);
		initFileSelectScene(stage);
		initResultTableScene(stage);
		stage.setScene(fileSelect);
		stage.show();
	}
	
	/*****************************
	 * ファイル選択画面（初期画面）の初期化
	 * @param stage
	 *****************************/
	public void initFileSelectScene(Stage stage) {
		
		Button lFileSelectButton = new Button("Log File");
		Button qFileSelectButton = new Button("Questionnaire File");
		Button analyzeButton = new Button("Analyze");
		VBox vbox = new VBox();
		Group root = new Group();
		CAFileChooser fc = new CAFileChooser();
		ProgressBar progressBar = new ProgressBar(0);
		Text progressText = new Text("");
		
		fileSelect = new Scene(root, 200, 200);
		progressBar.setVisible(false);
		
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
				
				if (lFiles.size() > 0 && qFile != null) {
					
					CATask caTask = new CATask(lFiles, qFile);
					
					progressBar.progressProperty().unbind();
					progressBar.progressProperty().bind(caTask.progressProperty());
					progressText.textProperty().bind(caTask.messageProperty());
					
					progressBar.setVisible(true);
					
					caTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

						@Override
						public void handle(WorkerStateEvent arg0) {
							progressBar.setVisible(false);
							List<CAResult> results = new ArrayList<CAResult>();
							results = caTask.getResults();
							resultTable.init();
							resultTable.makeList(results);
							stage.setScene(resultTable);
							stage.show();
						}
					});
					
					Executor executor = Executors.newSingleThreadExecutor();
					executor.execute(caTask);
				}
			}
		});
		
		vbox.getChildren().addAll(lFileSelectButton, qFileSelectButton, analyzeButton, progressBar, progressText);
		root.getChildren().addAll(vbox);
	}
	
	/*******************
	 * 分析結果画面の初期化
	 * @param stage
	 *******************/
	public void initResultTableScene(Stage stage) {
		StackPane root = new StackPane();
		resultTable = new CATableView(root, 750, 400);
		VBox vBox = new VBox();
		Button returnButton = new Button("return");
		
		//TODO returnボタンで戻って再検索すると値余分？
		returnButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				stage.setScene(fileSelect);
				stage.show();
			}
		});
		
		vBox.getChildren().add(resultTable.getTableView());
		vBox.getChildren().add(returnButton);
		root.getChildren().add(vBox);
	}

	public static void execFrame(String[] args) {
		launch();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
