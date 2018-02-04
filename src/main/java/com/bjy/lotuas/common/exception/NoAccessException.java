package com.bjy.lotuas.common.exception;

public class NoAccessException extends RuntimeException {
	private static final long serialVersionUID = 2371358080201618682L;

	public NoAccessException() {
		super();
	}

	public NoAccessException(String message, Throwable ex) {
		super(message, ex);
	}

	public NoAccessException(String message) {
		super(message);
	}

	public NoAccessException(Throwable ex) {
		super(ex);
	}
}
