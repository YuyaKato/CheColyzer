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
	 * CSV�t�@�C���̃��[�h
	 * @param file CSV�t�@�C��
	 * @return List<List<String>�`��
	 ********************************/
	public List<List<String>> loadCSV(File file) {
		List<List<String>> table = new ArrayList<List<String>>();
		table = CCSVFileIO.loadAsListList(CFileSystem.findFile(file.getAbsolutePath()));
		return table;
	}
	
	/******************
	 * ���͑Ώۂ̒l�̎擾
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
	 * �A���P�[�g����w�Дԍ����g���ĉ񓚌���
	 * @param table �A���P�[�g����CSV��List
	 * @param user �����Ώ�
	 * @return �Ώۍs
	 ***********************************/
	public List<String> search(List<List<String>> table, String user) {
		
		// TODO �A���P�[�g�񓚂��Ă��Ȃ������ꍇ�̏����i�t���j
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
		File qFile = new File("test/�i�v���O���~���O2014�j�ŏI�ۑ�O���[�v���[�N�Ɋւ���A���P�[�g�i�񓚁j - �t�H�[���̉� 1.csv");
		qr.reduct(qr.search(qr.loadCSV(qFile), "70411043"));
		System.out.println("SkillRank = " + qr.getSkillRank());
		System.out.println("DescriptionRate = " + qr.getDescriptionRate());
	}
}
