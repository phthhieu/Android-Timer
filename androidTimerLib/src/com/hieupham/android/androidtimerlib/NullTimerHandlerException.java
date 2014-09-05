package com.hieupham.android.androidtimerlib;

public class NullTimerHandlerException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private NullTimerHandlerException() {
		super("Haven't set handler to AndroidTimer yet");
	}
	
	public static void throwException() {
		throw new NullTimerHandlerException();
	}
}
