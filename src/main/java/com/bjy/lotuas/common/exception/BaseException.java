package com.bjy.lotuas.common.exception;
/**
 * 异常基类
 * @author 肖黎明
 * @history 2013-9-3
 */
public class BaseException extends Exception {
	
	private static final long serialVersionUID = 7834244179710116346L;

	public BaseException(String message,Throwable e){
        super(message, e);
    }
}
