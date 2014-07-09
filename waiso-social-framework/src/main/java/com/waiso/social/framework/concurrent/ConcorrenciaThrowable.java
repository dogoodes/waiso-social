package com.waiso.social.framework.concurrent;

/**
 * Classe que trata excecoes em objetos concorrentes, essa classe apenas
 * verifica a instancia correta da excecoes para poder lancar o tipo correto do
 * mesmo.
 * 
 * @author rodolfo.dias
 * 
 */
public final class ConcorrenciaThrowable {

	public static RuntimeException verificaThrowable(Throwable t) {
		if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		} else if (t instanceof Error) {
			throw (Error) t;
		} else {
			throw new IllegalStateException("Erro nao previsto.", t);
		}
	}

}
