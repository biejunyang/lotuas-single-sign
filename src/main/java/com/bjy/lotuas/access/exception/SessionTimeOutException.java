package com.bjy.lotuas.access.exception;

public class SessionTimeOutException extends RuntimeException{
	 
	public SessionTimeOutException() {
		super("login timeout!!");
	}

	public SessionTimeOutException(String message) {
		 super(message);
	 }

}
