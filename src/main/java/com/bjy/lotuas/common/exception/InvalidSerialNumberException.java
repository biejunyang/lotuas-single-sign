package com.bjy.lotuas.common.exception;

public class InvalidSerialNumberException extends RuntimeException {
	private static final long serialVersionUID = 6845091116237130724L;

	public InvalidSerialNumberException() {
		super();
	}

	public InvalidSerialNumberException(String message, Throwable err) {
		super(message, err);
	}

	public InvalidSerialNumberException(String message) {
		super(message);
	}

	public InvalidSerialNumberException(Throwable err) {
		super(err);
	}
}
