package com.waiso.social.framework.exceptions;

import com.waiso.social.framework.log.GerenciadorLog;

public class WSSendException extends UserException{
	
	private static final long serialVersionUID = 1L;
	
	public WSSendException(Class origin, Throwable cause) {
		super(cause);
		GerenciadorLog.error(origin, cause);
	}
	
	public WSSendException(Class origin, String message) {
		super(message);
		GerenciadorLog.error(origin, message);
	}
}