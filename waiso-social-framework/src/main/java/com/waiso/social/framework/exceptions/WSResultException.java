package com.waiso.social.framework.exceptions;

import com.waiso.social.framework.log.GerenciadorLog;

public class WSResultException extends UserException{
	
	private static final long serialVersionUID = 1L;
	
	public WSResultException(Class origin, Throwable cause) {
		super(cause);
		GerenciadorLog.error(origin, cause);
	}
	
	public WSResultException(Class origin, String message) {
		super(message);
		GerenciadorLog.error(origin, message);
	}
}