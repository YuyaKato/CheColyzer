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
	
	private int totalLoginTime = 0;
	private int allImportCount = 0;
	private int javaImportCount = 0;
	private int materialImportCount = 0;

	/************************
	 * CSVファイルのロード（リスト形式）
	 ************************/
	public List<List<String>> loadCSV(File file) {
		
		List<List<String>> table = new ArrayList<List<String>>();
		table = CCSVFileIO.loadAsListList(CFileSystem.findFile(file.getAbsolutePath()));
		return table;
	}
	
	/******************
	 * 総ログイン時間の計算
	 ******************/
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
	
	/*************************
	 * 開始時刻と終了時刻の差を計算
	 *************************/
	public long calcTime(String startTime, String endTime) {
		
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
