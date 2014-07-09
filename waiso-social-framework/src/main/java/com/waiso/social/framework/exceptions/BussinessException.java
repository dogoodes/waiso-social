package com.waiso.social.framework.exceptions;

public class BussinessException extends UserException{
	
	private static final long serialVersionUID = 1L;
	
	public BussinessException(String message) {
		super(message);
	}

	public BussinessException(String message, Throwable cause) {
		super(message, cause);
		
	}
}