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
	private int partialImportCount = 0;
	private int partialImportLine = 0;

	/********************************
	 * CSV�t�@�C���̃��[�h
	 * @param file CSV�t�@�C��
	 * @return List<List<String>�`��
	 ********************************/
	public List<List<String>> loadCSV(File file) {
		
		getUserFromFile(file);
		List<List<String>> table = new ArrayList<List<String>>();
		table = CCSVFileIO.loadAsListList(CFileSystem.findFile(file.getAbsolutePath()));
		return table;
	}
	
	/**********************
	 * �t�@�C��������w�Дԍ��擾
	 * @param file ���OCSV
	 **********************/
	public void getUserFromFile(File file) {
		String str = file.getName();
		str = str.substring(0, str.indexOf("_"));
		str = str.replace("-", "");
		setUser(str);
	}
	
	/*******************
	 * ���͑Ώۂ̒l�̃J�E���g
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
		boolean copy = false;
		int partialImportCount = 0;
		String code = "";
		int partialImportLine = 0;
		
		for (List<String> cells : table) {
			String command = cells.get(1);
			if (command.equals("login")) {
				startTime = cells.get(0);
				login = true;
			} else if (command.equals("logout") && login) {
				endTime = cells.get(0);
				if(calcTime(startTime, endTime) < 24*60*60){
					totalLoginTime += calcTime(startTime, endTime);
				}
				login = false;
			} else if (command.equals("copy all file")) {
				allImportCount++;
			} else if (command.equals("copy java file")) {
				javaImportCount++;
			} else if (command.equals("copy material fila")) {
				materialImportCount++;
			} else if (command.equals("copy code")) {
				code = cells.get(4);
				copy = true;
			} else if (command.equals("paste code") && copy) {
				if (cells.get(4).equals(code)) {
					partialImportCount++;
					code = code.replace("\\n", "\n");
					partialImportLine += code.split("\n").length;
				}
				copy = false;
			}
		}
		
		setTotalLoginTime((int) Math.round(totalLoginTime)/60);
		setAllImportCount(allImportCount);
		setJavaImportCount(javaImportCount);
		setMaterialImportCount(materialImportCount);
		setPartialImportCount(partialImportCount);
		setPartialImportLine(partialImportLine);
	}
	

	/**
	 * �J�n���ԂƏI�����Ԃ̍����v�Z
	 * @param startTime
	 * @param endTime
	 * @return ���ԍ��i�b�j
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

	public int getPartialImportCount() {
		return partialImportCount;
	}

	public void setPartialImportCount(int partialImportCount) {
		this.partialImportCount = partialImportCount;
	}

	public int getPartialImportLine() {
		return partialImportLine;
	}

	public void setPartialImportLine(int partialImportLine) {
		this.partialImportLine = partialImportLine;
	}

	public static void main(String[] args) {
		CALogReduct lr = new CALogReduct();
		File file = new File("test/70411069_CHLog.csv");
		lr.reduct(lr.loadCSV(file));
	}
}
