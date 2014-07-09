package com.waiso.social.framework.concurrent;
/**
 * Interface que define o comportamento das classes que trabalham em conjunto com o cache
 * 
 * @author rodolfo.dias
 *
 * @param <A> Argumento de Entrada
 * @param <V> Retorno esperado para o Argumento de Entrada informado
 */
public interface ICacheable<A, V> {
	V get(A arg) throws InterruptedException;
}
