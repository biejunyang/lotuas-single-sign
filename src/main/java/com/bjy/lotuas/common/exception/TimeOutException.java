package com.bjy.lotuas.common.exception;

public class TimeOutException extends RuntimeException {
	
	private static final long serialVersionUID = 7091298691852634907L;

	public TimeOutException() {
		super();
	}

	public TimeOutException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimeOutException(String message) {
		super(message);
	}

	public TimeOutException(Throwable cause) {
		super(cause);
	}
}
