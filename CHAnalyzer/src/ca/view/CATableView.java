package ca.view;

import java.util.List;

import ca.system.CAResult;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CATableView extends Scene {
	
	private final ObservableList<Standings> data = FXCollections.observableArrayList(new Standings("", 0, 0, 0, 0, 0, 0));
	private TableView<Standings> tableView = new TableView<Standings>();
	
	public CATableView(Parent arg0, double arg1, double arg2) {
		super(arg0, arg1, arg2);
	}
	
	/*********
	 * 初期化
	 *********/
	@SuppressWarnings("unchecked")
	public void init() {
		tableView.setEditable(false);
		
		TableColumn<Standings, String> studentIDCol = new TableColumn<Standings, String>("StudentID");
		studentIDCol.setMinWidth(80);
		studentIDCol.setCellValueFactory(new PropertyValueFactory<Standings, String>("studentID"));
		
		TableColumn<Standings, Integer> tltCol = new TableColumn<Standings, Integer>("LoginTime(m)");
		tltCol.setMinWidth(60);
		tltCol.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("totalLoginTime"));
		
		TableColumn<Standings, Integer> aicCol = new TableColumn<Standings, Integer>("Import(A)");
		aicCol.setMinWidth(30);
		aicCol.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("allImport"));
		
		TableColumn<Standings, Integer> jicCol = new TableColumn<Standings, Integer>("Import(JA)");
		jicCol.setMinWidth(30);
		jicCol.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("javaImport"));
		
		TableColumn<Standings, Integer> ricCol = new TableColumn<Standings, Integer>("Import(R)");
		ricCol.setMinWidth(30);
		ricCol.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("resourceImport"));
		
		TableColumn<Standings, Integer> srCol = new TableColumn<Standings, Integer>("Skill");
		srCol.setMinWidth(30);
		srCol.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("skillRank"));
		
		TableColumn<Standings, Integer> drCol = new TableColumn<Standings, Integer>("Description(%)");
		drCol.setMinWidth(30);
		drCol.setCellValueFactory(new PropertyValueFactory<Standings, Integer>("descriptionRate"));
		
		tableView.getColumns().addAll(studentIDCol, tltCol, aicCol, jicCol, ricCol, srCol, drCol);
	}
	
	/***************************
	 * CSVから取得した値をテーブルにセット
	 * @param results
	 ***************************/
	public void makeList(List<CAResult> results) {
		String studentID;
		int totalLoginTime;
		int allImport;
		int javaImport;
		int resourceImport;
		int skillRank;
		int descriptionRate;
		
		data.remove(0);
		for (CAResult result : results) {
			studentID = result.getLr().getUser();
			totalLoginTime = result.getLr().getTotalLoginTime();
			allImport = result.getLr().getAllImportCount();
			javaImport = result.getLr().getJavaImportCount();
			resourceImport = result.getLr().getMaterialImportCount();
			skillRank = result.getQr().getSkillRank();
			descriptionRate = result.getQr().getDescriptionRate();
			data.add(new Standings(studentID, totalLoginTime, allImport, javaImport, resourceImport, skillRank, descriptionRate));
		}
		tableView.setItems(data);
	}
	
	public TableView<Standings> getTableView() {
		return tableView;
	}

	public static class Standings {
		
		private final SimpleStringProperty studentID;
		private final SimpleIntegerProperty totalLoginTime;
		private final SimpleIntegerProperty allImport;
		private final SimpleIntegerProperty javaImport;
		private final SimpleIntegerProperty resourceImport;
		private final SimpleIntegerProperty skillRank;
		private final SimpleIntegerProperty descriptionRate;
		
		public Standings(String studentID, int totalLoginTime, int allImport,
				int javaImport, int resourceImport, int skillRank, int descriptionRate) {
			this.studentID = new SimpleStringProperty(studentID);
			this.totalLoginTime = new SimpleIntegerProperty(totalLoginTime);
			this.allImport = new SimpleIntegerProperty(allImport);
			this.javaImport = new SimpleIntegerProperty(javaImport);
			this.resourceImport = new SimpleIntegerProperty(resourceImport);
			this.skillRank = new SimpleIntegerProperty(skillRank);
			this.descriptionRate = new SimpleIntegerProperty(descriptionRate);
		}
		
		public String getStudentID() {
			return studentID.get();
		}
		
		public void setStudentID(String studentID) {
			this.studentID.set(studentID);
		}
		
		public int getTotalLoginTime() {
			return totalLoginTime.get();
		}
		
		public void setTotalLoginTime(int totalLoginTime) {
			this.totalLoginTime.set(totalLoginTime);
		}
		
		public int getAllImport() {
			return allImport.get();
		}
		
		public void setAllImport(int allImport) {
			this.allImport.set(allImport);
		}
		
		public int getJavaImport() {
			return javaImport.get();
		}
		
		public void setJavaImport(int javaImport) {
			this.javaImport.set(javaImport);
		}
		
		public int getResourceImport() {
			return resourceImport.get();
		}
		
		public void setResourceImport(int resourceImport) {
			this.resourceImport.set(resourceImport);
		}
		
		public int getSkillRank() {
			return skillRank.get();
		}
		
		public void setSkillRank(int skillRank) {
			this.skillRank.set(skillRank);
		}
		
		public int getDescriptionRate() {
			return descriptionRate.get();
		}
		
		public void setDescriptionRate(int descriptionRate) {
			this.descriptionRate.set(descriptionRate);
		}
	}
}
