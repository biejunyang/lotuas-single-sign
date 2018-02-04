package com.bjy.lotuas.common.exception;

public class SymmetryCryptException extends Exception {
	private static final long serialVersionUID = 149901663991151424L;

	public SymmetryCryptException() {
		super();
	}

	public SymmetryCryptException(String message, Throwable e) {
		super(message, e);
	}

	public SymmetryCryptException(String message) {
		super(message);
	}
	
	public SymmetryCryptException(Throwable e) {
		super(e);
	}
}
