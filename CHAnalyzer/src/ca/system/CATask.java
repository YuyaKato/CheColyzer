package ca.system;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;

public class CATask extends Task<Void> {

	private List<File> files = new ArrayList<File>();
	private List<CALogReduct> lrs = new ArrayList<CALogReduct>();
	
	public CATask(List<File> files) {
		super();
		this.files = files;
	}
	
	@Override
	protected Void call() throws Exception {
		for (File file : files) {
			lrs.add(reduct(file));
		}
		return null;
	}
	
	public CALogReduct reduct(File file) {
		CALogReduct lr = new CALogReduct();
		lr.reduct(lr.loadCSV(file));
		return lr;
	}

	public List<CALogReduct> getLrs() {
		return lrs;
	}

}
