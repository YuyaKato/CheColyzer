package ca.view;

import java.io.File;
import java.util.List;

import javafx.stage.FileChooser;

public class CAFileChooser {

	FileChooser fc;
	
	public CAFileChooser() {
		fc = new FileChooser();
		fc.setTitle("select log file");
	}
	
	public List<File> open() {
		return fc.showOpenMultipleDialog(null);
	}
}
