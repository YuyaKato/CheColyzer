package ca.app;

public class CAMain {

	public static void main(String[] args) {
		try {
			CAApplication.execFrame(args);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
