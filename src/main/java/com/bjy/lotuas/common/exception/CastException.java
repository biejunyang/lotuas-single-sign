
package com.bjy.lotuas.common.exception;

public class CastException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CastException(){
        super();
    }

    public CastException(String message){
        super(message);
    }

    public CastException(String message, Throwable cause){
        super(message, cause);
    }
}
