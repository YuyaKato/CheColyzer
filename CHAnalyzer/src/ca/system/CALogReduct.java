package ca.system;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clib.common.filesystem.CFileSystem;
import clib.common.table.CCSVFileIO;

public class CALogReduct {
	
	private String user = "";
	private int totalLoginTime = 0;
	private int allImportCount = 0;
	private int javaImportCount = 0;
	private int materialImportCount = 0;

	/********************************
	 * CSVファイルのロード
	 * @param file CSVファイル
	 * @return List<List<String>形式
	 ********************************/
	public List<List<String>> loadCSV(File file) {
		
		getUserFromFile(file);
		List<List<String>> table = new ArrayList<List<String>>();
		table = CCSVFileIO.loadAsListList(CFileSystem.findFile(file.getAbsolutePath()));
		return table;
	}
	
	/**********************
	 * ファイル名から学籍番号取得
	 * @param file ログCSV
	 **********************/
	public void getUserFromFile(File file) {
		String str = file.getName();
		str = str.substring(0, str.indexOf("_"));
		setUser(str);
	}
	
	/*******************
	 * 分析対象の値のカウント
	 * @param table
	 *******************/
	public void reduct(List<List<String>> table) {
		
		boolean login = false;
		String startTime = "";
		String endTime;
		double totalLoginTime = 0;
		int allImportCount = 0;
		int javaImportCount = 0;
		int materialImportCount = 0;
		
		for (List<String> cells : table) {
			String command = cells.get(1);
			if (command.equals("login")) {
				startTime = cells.get(0);
				login = true;
			} else if (command.equals("logout") && login) {
				endTime = cells.get(0);
				totalLoginTime += calcTime(startTime, endTime);
				login = false;
			} else if (command.equals("copy all file")) {
				allImportCount++;
			} else if (command.equals("copy java file")) {
				javaImportCount++;
			} else if (command.equals("copy material fila")){
				materialImportCount++;
			}
		}
		
		setTotalLoginTime((int) Math.round(totalLoginTime)/60);
		setAllImportCount(allImportCount);
		setJavaImportCount(javaImportCount);
		setMaterialImportCount(materialImportCount);
	}
	

	/**
	 * 開始時間と終了時間の差を計算
	 * @param startTime
	 * @param endTime
	 * @return 時間差（秒）
	 */
	private long calcTime(String startTime, String endTime) {
		
		try {
			Date start = DateFormat.getDateTimeInstance().parse(startTime);
			Date end = DateFormat.getDateTimeInstance().parse(endTime);
			long startLong  = start.getTime();
			long endLong = end.getTime();
			return (endLong - startLong)/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getTotalLoginTime() {
		return totalLoginTime;
	}

	public void setTotalLoginTime(int totalLoginTime) {
		this.totalLoginTime = totalLoginTime;
	}

	public int getAllImportCount() {
		return allImportCount;
	}

	public void setAllImportCount(int allImportCount) {
		this.allImportCount = allImportCount;
	}

	public int getJavaImportCount() {
		return javaImportCount;
	}

	public void setJavaImportCount(int javaImportCount) {
		this.javaImportCount = javaImportCount;
	}

	public int getMaterialImportCount() {
		return materialImportCount;
	}

	public void setMaterialImportCount(int materialImportCount) {
		this.materialImportCount = materialImportCount;
	}

	public static void main(String[] args) {
		CALogReduct lr = new CALogReduct();
		File file = new File("test/70411069_CHLog.csv");
		lr.reduct(lr.loadCSV(file));
	}
}
