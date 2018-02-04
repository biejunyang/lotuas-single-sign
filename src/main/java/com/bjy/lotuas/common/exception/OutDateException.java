package com.bjy.lotuas.common.exception;

public class OutDateException extends RuntimeException {
	private static final long serialVersionUID = 6845091116237130724L;

	public OutDateException() {
		super();
	}

	public OutDateException(String message, Throwable err) {
		super(message, err);
	}

	public OutDateException(String message) {
		super(message);
	}

	public OutDateException(Throwable err) {
		super(err);
	}
}
