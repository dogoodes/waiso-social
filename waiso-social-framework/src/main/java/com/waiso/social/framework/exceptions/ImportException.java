package com.waiso.social.framework.exceptions;

import com.waiso.social.framework.log.GerenciadorLog;

public class ImportException extends UserException{
	
	private static final long serialVersionUID = 1L;
	
	public ImportException(Class origin, Throwable cause) {
		super(cause);
		GerenciadorLog.error(origin, cause);
	}
	
	public ImportException(Class origin, String message) {
		super(message);
		GerenciadorLog.error(origin, message);
	}
	
	public ImportException(String message) {
		super(message);
	}
}