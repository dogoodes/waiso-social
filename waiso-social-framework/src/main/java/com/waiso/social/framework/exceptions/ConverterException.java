package com.waiso.social.framework.exceptions;

import com.waiso.social.framework.log.GerenciadorLog;

public class ConverterException extends UserException{
	
	private static final long serialVersionUID = 1L;
	
	public ConverterException(Class origin, Throwable cause) {
		super(cause);
		GerenciadorLog.error(origin, cause);
	}
	
	public ConverterException(Class origin, String message) {
		super(message);
		GerenciadorLog.error(origin, message);
	}
}