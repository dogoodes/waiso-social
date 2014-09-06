package com.waiso.social.framework.exceptions;

import com.waiso.social.framework.log.GerenciadorLog;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserException() {
		super();
	}
	
	public UserException(Throwable cause) {
		this(null, cause);
	}

	public UserException(String message) {
		super(message);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
		if (GerenciadorLog.isDebug(UserException.class)) {
			GerenciadorLog.debug(UserException.class, cause, message);
		}
	}
	
	public UserException(Class<?> srcClass, String message, Throwable cause) {
		super(message);
	}
}