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

	public List<List<String>> loadCSV(File file) {
		
		List<List<String>> table = new ArrayList<List<String>>();
		table = CCSVFileIO.loadAsListList(CFileSystem.findFile(file.getAbsolutePath()));
		return table;
	}
	
	public void calcTotalLoginTime(List<List<String>> table) {
		
		boolean login = false;
		String startTime = "";
		String endTime;
		double loginTime = 0;
		
		for (List<String> cells : table) {
			if (cells.get(1).equals("login")) {
				startTime = cells.get(0);
				login = true;
			} else if (cells.get(1).equals("logout") && login) {
				endTime = cells.get(0);
				loginTime += calcTime(startTime, endTime);
				login = false;
			}
		}
		System.out.println(loginTime/60);
	}
	
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
	
	public static void main(String[] args) {
		CALogReduct lr = new CALogReduct();
		File file = new File("test/70411069_CHLog.csv");
		lr.calcTotalLoginTime(lr.loadCSV(file));
	}
}
