package com.waiso.social.framework.exceptions;

import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.log.GerenciadorLog;

public class CriticalUserException extends UserException {

	private static final long serialVersionUID = -8280945186537174047L;

	public CriticalUserException(Class<?> origin, String messageSystem) {
		super(GerenciadorMensagem.getMessage(GerenciadorMensagem.ERROR_GERAL));
		GerenciadorLog.critical(origin, messageSystem);
	}

	public CriticalUserException(Class<?> origin, String messageSystem, Throwable error){
		super(GerenciadorMensagem.getMessage(GerenciadorMensagem.ERROR_GERAL), error);
		GerenciadorLog.critical(origin, error, messageSystem);
	}
	
	public CriticalUserException(Class<?> origin, String messageSystem, String messageUser, Throwable error){
		super(messageUser, error);
		GerenciadorLog.critical(origin, error, messageSystem);
	}
}