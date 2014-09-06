package com.waiso.social.framework.exceptions;

import com.waiso.social.framework.log.GerenciadorLog;

public class FileException extends UserException {
	
	private static final long serialVersionUID = 1L;
	
	public FileException(Class origin, Throwable cause) {
		super(cause);
		GerenciadorLog.error(origin, cause);
	}
	
	public FileException(Class origin, String message) {
		super(message);
		GerenciadorLog.error(origin, message);
	}
	
	public FileException(String message) {
		super(message);
	}
}