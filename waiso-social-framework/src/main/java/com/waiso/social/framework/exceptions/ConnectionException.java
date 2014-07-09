package com.waiso.social.framework.exceptions;

import com.waiso.social.framework.log.GerenciadorLog;

public class ConnectionException extends UserException {

	private static final long serialVersionUID = 1L;
	
	public ConnectionException(Class origin, Throwable cause) {
		super(cause);
		GerenciadorLog.error(origin, cause);
	}
	
	public ConnectionException(Class origin, String message) {
		super(message);
		GerenciadorLog.error(origin, message);
	}
}