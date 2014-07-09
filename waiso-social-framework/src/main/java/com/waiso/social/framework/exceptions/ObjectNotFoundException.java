package com.waiso.social.framework.exceptions;

import com.waiso.social.framework.log.GerenciadorLog;

public class ObjectNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Use preferencialmente essa caso seja para demonstrar que nao foi encontrado
	 * um objeto por um atributo diferente da chave que o identifica como unico.
	 */
	public ObjectNotFoundException(){
		super("");
	}
	
	/**
	 * Construtor com a entrada da excecao e deve ser utilizada quando existe a necessidade
	 * de se logar a informacao, geralmente quando a busca � feita por ID do objeto
	 * 
	 * @param cause
	 */
	public ObjectNotFoundException(Class origin, Throwable cause) {
		super(cause);
		GerenciadorLog.error(origin, cause);
	}

	
	/**
	 * Utilizar esse construtor quando existir a real necessidade de se logar a excecao, 
	 * @param origin
	 * @param message
	 */
	public ObjectNotFoundException(Class origin, String message) {
		super(message);
		GerenciadorLog.error(origin, message);
	}

	/**
	 * Construtor da excecao que recebe como parametro uma mensagem descritiva
	 * sobre a natureza do erro que reflete, assim como a excecao original se
	 * houvesse.
	 * 
	 * @param message texto descritivo da excecao.
	 * @param cause excecao original a ser estabelecida como causa.
	 */
	public ObjectNotFoundException(Class origin, String message, Throwable cause) {
		super(message, cause);
		GerenciadorLog.error(origin, cause, message);
	}
}