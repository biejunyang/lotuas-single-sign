package com.bjy.lotuas.common.exception;

public class NotInstallException extends RuntimeException {
	private static final long serialVersionUID = 6845091116237130724L;

	public NotInstallException() {
		super();
	}

	public NotInstallException(String message, Throwable err) {
		super(message, err);
	}

	public NotInstallException(String message) {
		super(message);
	}

	public NotInstallException(Throwable err) {
		super(err);
	}
}
