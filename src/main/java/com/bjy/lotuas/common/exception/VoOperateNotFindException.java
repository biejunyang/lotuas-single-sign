package com.bjy.lotuas.common.exception;

public class VoOperateNotFindException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public VoOperateNotFindException() {
		super();
	}

	public VoOperateNotFindException(String message, Throwable cause) {
		super(message, cause);
	}

	public VoOperateNotFindException(String message) {
		super(message);
	}

	public VoOperateNotFindException(Throwable cause) {
		super(cause);
	}
}
