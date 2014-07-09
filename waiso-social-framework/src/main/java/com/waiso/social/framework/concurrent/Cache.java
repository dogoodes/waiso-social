package com.waiso.social.framework.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Esta classe cria um mecanismo de cache. O Cache e criado atraves de uma
 * estrutura interna que pode ser acessado concorrentemente.
 * 
 * Solucao retirada do livro: Java concurrency in Practice - Brian Goetz
 *
 * @author rodolfo.dias
 * 
 * @param <A>
 *            Argumento de entrada para busca no cache
 * @param <V>
 *            Retorno do objeto esperado atraves da busca no cache
 */
public class Cache<A, V> implements ICacheable<A, V> {
	private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final ICacheable<A, V> tarefa;

	public Cache(ICacheable<A, V> tarefa) {
		this.tarefa = tarefa;
	}

	public V get(final A arg) throws InterruptedException {
		while (true) {
			Future<V> f = cache.get(arg);
			if (f == null) {
				Callable<V> eval = new Callable<V>() {
					public V call() throws InterruptedException {
						return tarefa.get(arg);
					}
				};
				FutureTask<V> ft = new FutureTask<V>(eval);
				f = cache.putIfAbsent(arg, ft);
				if (f == null) {
					f = ft;
					ft.run();
				}
			}
			try {
				return f.get();
			} catch (CancellationException e) {
				cache.remove(arg, f);
			} catch (ExecutionException e) {
				ConcorrenciaThrowable.verificaThrowable(e);
			}
		}
	}
}
