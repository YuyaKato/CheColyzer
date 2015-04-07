package ca.view;

import java.io.File;
import java.util.List;

import javafx.stage.FileChooser;

public class CAFileChooser {

	FileChooser fc;
	
	public CAFileChooser() {
		fc = new FileChooser();
		fc.setTitle("select file");
	}
	
	public List<File> openMultiple() {
		fc.setTitle("select log files");
		return fc.showOpenMultipleDialog(null);
	}
	
	public File openSingle() {
		fc.setTitle("select questionnaire file");
		return fc.showOpenDialog(null);
	}
}
