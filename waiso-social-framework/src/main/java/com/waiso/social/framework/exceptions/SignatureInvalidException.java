package com.waiso.social.framework.exceptions;

import com.waiso.social.framework.log.GerenciadorLog;

public class SignatureInvalidException extends UserException{
	
	private static final long serialVersionUID = 1L;
	
	public SignatureInvalidException(Class origin, Throwable cause) {
		super(cause);
		GerenciadorLog.error(origin, cause);
	}
	
	public SignatureInvalidException(Class origin, String message) {
		super(message);
		GerenciadorLog.error(origin, message);
	}
}