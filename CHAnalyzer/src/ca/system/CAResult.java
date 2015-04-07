package ca.system;


public class CAResult {
	
	private CALogReduct lr;
	private CAQuestionnaireReduct qr;
	
	public CAResult(CALogReduct lr, CAQuestionnaireReduct qr) {
		this.lr = lr;
		this.qr = qr;
	}

	public CALogReduct getLr() {
		return lr;
	}

	public CAQuestionnaireReduct getQr() {
		return qr;
	}
}
