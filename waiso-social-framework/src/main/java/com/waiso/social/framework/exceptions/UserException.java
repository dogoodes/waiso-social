package com.waiso.social.framework.exceptions;

import com.waiso.social.framework.log.GerenciadorLog;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor com a entrada da excecao
	 * 
	 * @param cause
	 */
	public UserException(Throwable cause) {
		this(null, cause);
		// GerenciadorLog.error(cause);
	}

	public UserException(String message) {
		super(message);
	}

	/**
	 * Construtor da excecao que recebe como parametro uma mensagem descritiva
	 * sobre a natureza do erro que reflete, assim como a excecao original se
	 * houvesse.
	 * 
	 * @param message texto descritivo da excecao.
	 * @param cause excecao original a ser estabelecida como causa.
	 */
	public UserException(String message, Throwable cause) {
		super(message, cause);
		if(GerenciadorLog.isDebug(UserException.class)){
			GerenciadorLog.debug(UserException.class, cause, message);
		}
	}
	
	/**
	 * Utilizando esse construtor o log sera acionados
	 * @param srcClass
	 * @param message
	 * @param cause
	 */
	public UserException(Class<?> srcClass, String message, Throwable cause) {
		super(message);
	}
}
