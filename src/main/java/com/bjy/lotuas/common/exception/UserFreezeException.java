package com.bjy.lotuas.common.exception;

public class UserFreezeException extends RuntimeException {
	private static final long serialVersionUID = 7243420081217738315L;

	public UserFreezeException() {
		super();
	}

	public UserFreezeException(String messgae, Throwable ex) {
		super(messgae, ex);
	}

	public UserFreezeException(String messgae) {
		super(messgae);
	}
	
	public UserFreezeException(Throwable ex) {
		super(ex);
	}
}
