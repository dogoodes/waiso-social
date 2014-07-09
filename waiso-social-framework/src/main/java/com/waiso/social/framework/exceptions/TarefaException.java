package com.waiso.social.framework.exceptions;

import com.waiso.social.framework.log.GerenciadorLog;

public class TarefaException extends UserException {

	private static final long serialVersionUID = 1L;

	public TarefaException(Class origin, Throwable cause) {
		super(cause);
		GerenciadorLog.error(origin, cause);
	}
	
	public TarefaException(Class origin, String message) {
		super(message);
		GerenciadorLog.error(origin, message);
	}
}