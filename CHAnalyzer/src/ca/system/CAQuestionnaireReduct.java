package ca.system;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import clib.common.filesystem.CFileSystem;
import clib.common.table.CCSVFileIO;

public class CAQuestionnaireReduct {
	
	public static final int INDEX_OF_USER = 1;
	public static final int INDEX_OF_SKILLRANK = 10;
	public static final int INDEX_OF_DESCRIPTIONRATE = 11;
	
	private int skillRank;
	private int descriptionRate;

	/********************************
	 * CSVファイルのロード
	 * @param file CSVファイル
	 * @return List<List<String>形式
	 ********************************/
	public List<List<String>> loadCSV(File file) {
		List<List<String>> table = new ArrayList<List<String>>();
		table = CCSVFileIO.loadAsListList(CFileSystem.findFile(file.getAbsolutePath()));
		return table;
	}
	
	/******************
	 * 分析対象の値の取得
	 * @param cells
	 ******************/
	public void reduct(List<String> cells) {
		String skillRank = cells.get(INDEX_OF_SKILLRANK);
		String descriptionRate = cells.get(INDEX_OF_DESCRIPTIONRATE);
		skillRank = skillRank.substring(0, 1);
		this.skillRank = Integer.parseInt(skillRank);
		this.descriptionRate = Integer.parseInt(descriptionRate);
	}
	
	/***********************************
	 * アンケートから学籍番号を使って回答検索
	 * @param table アンケート結果CSVのList
	 * @param user 検索対象
	 * @return 対象行
	 ***********************************/
	public List<String> search(List<List<String>> table, String user) {
		
		// TODO アンケート回答していなかった場合の処理（逆も）
		for (List<String> cells : table) {
			if (cells.get(INDEX_OF_USER).equals(user)) {
				return cells;
			}
		}
		return null;
	}

	public int getSkillRank() {
		return skillRank;
	}

	public int getDescriptionRate() {
		return descriptionRate;
	}
	
	public static void main(String[] args) {
		CAQuestionnaireReduct qr = new CAQuestionnaireReduct();
		File qFile = new File("test/（プログラミング2014）最終課題グループワークに関するアンケート（回答） - フォームの回答 1.csv");
		qr.reduct(qr.search(qr.loadCSV(qFile), "70411043"));
		System.out.println("SkillRank = " + qr.getSkillRank());
		System.out.println("DescriptionRate = " + qr.getDescriptionRate());
	}
}
