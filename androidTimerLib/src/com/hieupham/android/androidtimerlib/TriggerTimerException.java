package com.hieupham.android.androidtimerlib;

public class TriggerTimerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private TriggerTimerException(String message) {
		super(message);
	}
	
	public static void throwTriggerStartingTimerException() {
		throw new TriggerTimerException("Timer is started already");
	}
	
	public static void throwTriggerStoppingTimerException() {
		throw new TriggerTimerException("Timer is stopped already or haven't started yet");
	}
}
