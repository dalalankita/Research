package org.jersey.demo.messenger;

public class TourLogger {
	
	private static TourLogger logger = null;

	private TourLogger() {

	}
	public static TourLogger getInstance() {
		if(logger == null) {
			logger = new TourLogger();
		}
		return logger;
	}

	public void info(String value) {
		System.out.println("TourPlanApplication:::::: "+value);
	}
}
