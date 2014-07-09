package com.waiso.social.framework.queue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;
import com.waiso.social.framework.exceptions.TarefaException;
import com.waiso.social.framework.log.GerenciadorLog;

/**
 * Classe responsavel pelo Gerenciamento de Fila, e pode ser usado por
 * consumidores de tarefas e provedores de tarefas. A fila e dimensionada de
 * acordo com o parametro TAM_FILA e quando esse valor e atingido a fila eh
 * bloqueada para novas entradas ate que exista espaco vago na fila, o mesmo
 * acontece quando a fila esta vazia e consumidores tentam recuperar informacoes
 * na fila, ou seja, o consumidor tambem eh bloqueado ate que algo exista na fila
 * para ser consumido. Caso a classe cliente que utiliza o Gerenciador de Filas
 * nao tenha mais objetos para inserir na fila, entao o mesmo deve acionar o
 * metodo <code>shutdown</code>, como boa pratico e altamente recomendado que
 * essa chamada esteja em um bloco <code>finally</code>. O metodo shutdown e
 * responsavel por inserir um objeto <code>PoisonPill</code> que devera ser
 * lido por consumidores <code>Consumidor</code> como sinalizador de fim de
 * dados na fila.
 * </p>
 * <p>
 * <b>Um ponto de atencao</b> e que a classe GerenciadorTarefas eh um singleton
 * e que <i>possue apenas uma fila</i>, ou seja, os resultados que deverao ser
 * consumidos devem ser todos gerados pela mesma classe Produtora, para que um
 * consumidor nao pegue um resultado de um provedor nao previsto.
 * </p>
 * 
 * @author rodolfo.dias
 * 
 */
public class GerenciadorFilas {

	private static final int tamFila = Integer.parseInt(GerenciadorConfiguracao.getConfiguracao("TAM_FILA"));

	private final BlockingDeque<Object> fila;

	public static final GerenciadorFilas instance = new GerenciadorFilas();

	private GerenciadorFilas() {
		fila = new LinkedBlockingDeque<Object>(tamFila);
	}

	public static GerenciadorFilas getInstance() {
		return instance;
	}

	/**
	 * Metodo responsavel por adicionar um objeto na fila para ser consumido. O
	 * objeto sera inserida na fila caso exista espaco vago, senao ficara em
	 * lock esperando que algum objeto dentro da fila seja consumido.
	 * 
	 * @param resultado
	 *            Objeto que sera consumido.
	 * @throws InterruptedException
	 */
	public void addProdutor(Object resultado) throws InterruptedException {
		fila.putLast(resultado);
	}

	/**
	 * Metodo responsavel por retornar um resultado contido na fila.
	 * 
	 * @return O Resultado de uma tarefa adicionada atraves do metodo
	 *         addProdutor
	 * @throws TarefaException
	 *             Caso a tarefa tenha lancado alguma excecao no seu
	 *             processamento.
	 */
	public Object getResultadoProdutor() throws InterruptedException {
		return fila.takeFirst();
	}

	public void shutdown() {
		int nrThreads = Integer.parseInt(GerenciadorConfiguracao
				.getConfiguracao("QT_THREADS_PROCESSO"));
		for (int i = 0; i < nrThreads; i++) {
			try {
				addProdutor(PoisonPill.SINGLETON);
			} catch (InterruptedException e) {
				GerenciadorLog.debug(GerenciadorFilas.class, "Fila foi cancelada.");
			}
		}
	}
}
