package ca.system;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;

public class CATask extends Task<Void> {

	private List<File> lFiles = new ArrayList<File>();
	private File qFile;
	private List<CAResult> results = new ArrayList<CAResult>();
	
	public CATask(List<File> lFiles, File qFile) {
		super();
		this.lFiles = lFiles;
		this.qFile = qFile;
	}
	
	@Override
	protected Void call() throws Exception {
		int i = 0;
		for (File lFile : lFiles) {
			updateProgress(i, lFiles.size());
			updateMessage(String.format("analyzing %d/%d", i, lFiles.size()));
			CALogReduct lr = lReduct(lFile);
			CAQuestionnaireReduct qr = qReduct(qFile, lr.getUser());
			results.add(new CAResult(lr, qr));
			i++;
		}
		updateMessage("Done");
		return null;
	}
	
	private CALogReduct lReduct(File file) {
		CALogReduct lr = new CALogReduct();
		lr.reduct(lr.loadCSV(file));
		return lr;
	}
	
	private CAQuestionnaireReduct qReduct(File file, String user) {
		CAQuestionnaireReduct qr = new CAQuestionnaireReduct();
		qr.reduct(qr.search(qr.loadCSV(file), user));
		return qr;
	}

	public List<CAResult> getResults() {
		return results;
	}

}
