package ca.view;

import java.io.File;
import javafx.stage.FileChooser;

public class CAFileChooser {

	FileChooser fc;
	
	public CAFileChooser() {
		fc = new FileChooser();
		fc.setTitle("select log file");
	}
	
	public File open() {
		return fc.showOpenDialog(null);
	}
}
